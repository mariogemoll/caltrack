import { nest } from 'd3-collection';
import { select, event } from 'd3-selection';
import { timeFormat } from 'd3-time-format';
import { json } from 'd3-request';
import { getUrlVars, identity, sum } from './util';
import * as t from './time';

var vars = getUrlVars();
var startDate, endDate;

try {
  startDate = t.startOfDay(vars.from);
  endDate = t.endOfDay(vars.to);
} catch (e) {
  startDate = t.startOfLastMonday();
  endDate = t.endOfNextSunday();
}

buildPage(startDate, endDate);

function buildPage(startDate, endDate) {
  var from = encodeURIComponent(t.rfc3339(startDate)),
      to = encodeURIComponent(t.rfc3339(endDate));
  select('#from').attr('value', t.yyyymmddDashed(startDate));
  select('#to').attr('value', t.yyyymmddDashed(endDate));
  json('/_/events?from=' + from + '&to=' + to, handleJson);
}

function handleJson(err, obj) {
  var events, groupedEvents, tbody, tr, hhmm, totalTime;
  events = obj.events.map((e) => {
    e.date = e.start.substring(0,10);
    var startDate = new Date(e.start);
    e.date = t.yyyymmddDashed(startDate);
    e.start = new Date(startDate);
    e.end = new Date(e.end);
    e.duration = e.end - e.start;
    var firstSpacePos = e.title.indexOf(' ');
    if (firstSpacePos === -1) {
      e.project = e.title;
      e.description = '';
    } else {
      e.project = e.title.substr(0, firstSpacePos);
      e.description = e.title.substr(firstSpacePos + 1);
    }
    return e;
  });

  groupedEvents = nest()
    .key((e) => e.date)
    .rollup((events) => {
      var total = 0;
      var projects = {};
      for (var i = 0; i < events.length; i++) {
        var e = events[i];
        total += e.duration;
        if (projects[e.project] === undefined) {
          projects[e.project] = 0;
        }
        projects[e.project] += e.duration;
      }
      return {
        total: total,
        projects: projects,
        events: events
      };
    })
    .entries(events);

  tbody = select('#entries')
    .selectAll('tbody')
    .data(groupedEvents)
    .enter()
    .append('tbody');
  
  tbody.append('tr')
    .classed('date-separator', true)
    .append('td')
    .attr('colspan', 4)
    .text((group) => {
      var projects = Object.keys(group.value.projects);
      projects.sort();
      var projectTotals = projects.map((p) => p + ' ' + t.humanTime(group.value.projects[p]));
      var summary = '(' + projectTotals.join(', ') + ')';
      return group.key + ' : ' + t.humanTime(group.value.total) + ' ' + summary;
    });

  tr = tbody.selectAll('tr.entry')
    .data((group) => group.value.events)
    .enter().append('tr').classed('entry', true);

  hhmm = timeFormat('%H:%M');
  tr.append('td').text((e) => hhmm(e.start) + "–" + hhmm(e.end));
  tr.append('td').text((e) => t.humanTime(e.duration));
  tr.append('td').text((e) => e.project);
  tr.append('td').text((e) => e.description);

  // display total time
  totalTime = groupedEvents.map((topLevel) => topLevel.value.total).reduce(sum, 0);
  select('#total').text(t.humanTime(totalTime));
}
