import { pad } from './util';

/**
 * Convert a timespan into a human-readable format, e.g. "1h 30min"
 */
export function humanTime(milliseconds) {
  var hours = milliseconds/3600000;
  var fullHours = Math.floor(hours);
  var minutes = Math.round((hours-fullHours) * 60);
  var textparts = [];
  if (fullHours > 0) {
    textparts.push(fullHours + "h");
  }
  if (minutes > 0) {
    textparts.push(minutes + "min");
  }
  return textparts.join(" ");
};

/**
 * Creates a Date object at 00:00:00 on the date "dd-mm-yyyy"
 * @param {string} date - The date as "dd-mm-yyyy"
 */
export function startOfDay(date) {
  var year = date.substring(0,4),
      month = parseInt(date.substring(5,7), 10) - 1,
      day = parseInt(date.substring(8,10), 10),
      dateObj = new Date(year, month, day);
  dateObj.setHours(0,0,0,0);
  return dateObj;
}

/**
 * Creates a Date object at 23:59:59:999 on the date "dd-mm-yyyy"
 * @param {string} date - The date as "dd-mm-yyyy"
 */
export function endOfDay(date) {
  var year = date.substring(0,4),
      month = parseInt(date.substring(5,7), 10) - 1,
      day = parseInt(date.substring(8,10), 10),
      dateObj = new Date(year, month, day);
  dateObj.setHours(23,59,59,999);
  return dateObj;
}

/**
 * Creates a Date object at 00:00:00 on last Monday
 */
export function startOfLastMonday() {
  var noon = new Date(),
      lastMonday;
  noon.setHours(12,0,0,0,0),
  lastMonday = new Date(noon.getTime() - ((noon.getDay() + 6) % 7) * 86400000);
  lastMonday.setHours(0,0,0,0);
  return lastMonday;
}

/**
 * Creates a Date object at 23:59:59:999 on next Sunday
 */
export function endOfNextSunday() {
  var nextSunday = new Date(startOfLastMonday().getTime() + 6 * 86400000);
  nextSunday.setHours(23,59,59,999);
  return nextSunday;
}

export function rfc3339(date) {
  return pad(date.getUTCFullYear(), 4)
    + "-" + pad(date.getUTCMonth() + 1, 2)
    + "-" + pad(date.getUTCDate(), 2)
    + "T" + pad(date.getUTCHours(), 2)
    + ":" + pad(date.getUTCMinutes(), 2)
    + ":" + pad(date.getUTCSeconds(), 2)
    + "Z";
};


export function yyyymmddDashed(date) {
  return [pad(date.getFullYear(), 4),
          pad(date.getMonth() + 1, 2),
          pad(date.getDate(), 2)].join("-");
};
