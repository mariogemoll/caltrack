import 'whatwg-fetch';

function el(id) {
    return document.getElementById(id);
}

function make(tagName) {
  return document.createElement(tagName);
}

var loginForm = el('login-form');

loginForm.onsubmit = handleFormSubmit;

function handleFormSubmit() {
  fetch("/_/login", {
    method: "POST",
    headers: new Headers({
      'Content-Type': 'application/json'
    }),
    body: JSON.stringify({ passphrase: el('passphrase').value }),
    credentials: 'same-origin'
  }).then(function(res) {
    if (res.status === 403) {
      alert("Wrong passphrase.");
    } else if ((res.status) !== 200) {
      alert("An error occurred.");
    } else {
      window.location.href = "/report";
    }
  }).catch(function(ex) {
    alert("An error occurred.");
  });
  return false;
}
