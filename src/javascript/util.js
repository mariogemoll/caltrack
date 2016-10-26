/**
  * Parses the url and returns an object with the query string params
  */
export function getUrlVars() {
  var vars = {};
  var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
    vars[key] = value;
  });
  return vars;
}

/**
 * The identity function
 */
export function identity(x) {
  return x;
}

/**
 * Sum up two numbers
 */
export function sum(a, b) {
  return a + b;
}

export function pad(num, length) {
  return (Array(length).join('0') + num).slice(-length);
};
