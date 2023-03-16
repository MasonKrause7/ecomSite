//TESTS

/* WORKING 100% - LOADING 7 HEADPHONES TO A CART
function testLoadCart(){
  Reflow.cart.addProduct(595678991);
  Reflow.cart.addProduct(595678991);
  Reflow.cart.addProduct(595678991);
  Reflow.cart.addProduct(595678991);
  Reflow.cart.addProduct(595678991);
  Reflow.cart.addProduct(595678991);
  Reflow.cart.addProduct(595678991);
}
*/

//=====END TESTS=====
//==========START SIGNUP AND HELPERS==========
/*
signUp() is called from the onclick attribute of the "SIGN UP" button on the signup.html page.
Initiates async POST to /api/users/signup, with the user object in the body of the request.
If POST is successful -> Saves users email in local storage.
  If response message is "User already exists" -> redirects to the login and alerts user that the email is already registered
  Else If response message is anything else -> redirects to login page and automatically loads their email into the email input field 
Else If POST fails -> (UNLIKELY ERROR) logs error in console and alerts user to refresh browser
*/
async function signUp() {
  const controllerURL = "http://localhost:8080/api/users/signup";
  let user = getSignUpObj();

  await fetch(controllerURL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: user,
  })
    .then((response) =>
      response.json().then((data) => ({ status: response.status, body: data }))
    )
    .then((obj) => {
      console.log("data fulfilled: ", obj.body);
      localStorage.setItem("email", obj.body.email);
      localStorage.setItem(
        "email2",
        document.getElementById("emailInput").value
      );

      if (obj.body.message === "User already exists") {
        setTimeout(function () {
          window.location.href = "login.html";
        }, 500);
        window.alert(
          "That email is already registered, redirecting to login..."
        );
      } else {
        console.log(`${localStorage.email} successfully signed up.`);
        window.location.href = "login.html";
      }
    })
    .catch((error) => {
      console.log("Error in signUp(): ", error);
      window.alert(
        "Error signing up, please refresh your browser and try again."
      );
    });
}
/*
getSignUpObj() is a helper method for signUp().
Handles getting all the values from the signup form and returns a javascript object who's fields match a user object's. 
**NEED TO HANDLE FORM VALIDATION HERE**
*/
function getSignUpObj() {
  var jsObj = {
    firstName: document.getElementById("firstName").value,
    lastName: document.getElementById("lastName").value,
    email: document.getElementById("emailInput").value,
    password: document.getElementById("passwordInput").value,
    phoneNumber: document.getElementById("phoneNumber").value,
    address: [
      {
        houseNumber: document.getElementById("streetNum").value,
        street: document.getElementById("streetName").value,
        city: document.getElementById("city").value,
        state: document.getElementById("state").value,
        postalCode: document.getElementById("zipcode").value,
        country: "USA",
      },
    ],
    authority: "USER",
  };
  return JSON.stringify(jsObj);
}

//==========END SIGNUP AND HELPERS==========

/*fillEmail() is called from the login.html page body elements onload attribute (everytime login.html is loaded)
helps take the email addy from the signup form over to
the login page and fills the email field so that the user
only has to enter there password to login to their new account.
Eliminates redundant typing for the user.
*/
function fillEmail() {
  setTimeout(function () {
    localStorage.clear();
  }, 10000);
  var emailAddy;
  var email2 = localStorage.getItem("email2");
  if (email2 === "") {
    emailAddy = localStorage.getItem("email");
  } else {
    emailAddy = email2;
  }
  document.getElementById("email").value = emailAddy;
}

//==========START LOGIN AND LOGOUT==========
/*
login() is called from the login.html page from the "LOGIN" buttons onclick attribute.
Takes the email address from the input field and the password and encodes them Base64 for the Authorization
If request is successful {
  The response contains a userDTO, with the fields: userId, firstName, lastName, email, phoneNumber, and authority.
  Depending on the users authority, they are rerouted to their respective landing pages (Admin-adminpanel.html, Employee-employeepanel.html, User-homepage.html)
}
Else If request fails{
  logs the error, displays a message to the user that the credentials entered were invalid.
}
*/
async function login() {
  const controllerURL = "http://localhost:8080/api/users/signin";

  localStorage.clear();

  let email1 = document.getElementById("email").value;
  let pass1 = document.getElementById("password").value;
  let encodedData = "Basic " + window.btoa(`${email1}:${pass1}`);

  await fetch(controllerURL, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      Authorization: encodedData,
    },
  })
    .then((response) =>
      response.json().then((data) => ({ status: response.status, body: data }))
    )
    .then((obj) => {
      localStorage.setItem("authority", obj.body.authority);
      localStorage.setItem("firstName", obj.body.firstName);
      localStorage.setItem("lastName", obj.body.lastName);
      localStorage.setItem("email", obj.body.email);
      localStorage.setItem("phoneNumber", obj.body.phoneNumber);

      var addy = obj.body.address;
      const address = addy[0];
      console.log(address);

      localStorage.setItem("addressId", address.addressId);
      localStorage.setItem("houseNumber", address.houseNumber);
      localStorage.setItem("street", address.street);
      localStorage.setItem("city", address.city);
      localStorage.setItem("state", address.state);
      localStorage.setItem("zipcode", address.postalCode);

      console.log("Contents of local storage: ");
      for (var i = 0; i < localStorage.length; i++) {
        console.log(
          localStorage.key(i) +
            " : " +
            localStorage.getItem(localStorage.key(i))
        );
      }

      switch (localStorage.getItem("authority")) {
        case "ADMIN":
          window.location.href = "adminpanel.html";
          break;
        case "USER":
          window.location.href = "homepage.html";
          break;
        case "EMPLOYEE":
          window.location.href = "employeepanel.html";
          break;
      }
    })

    .catch((error) => {
      console.log("invalid login credentials:" + error);
      document.getElementById("hiddenMessage").innerText =
        "Invalid login information, please try again or sign up instead.";
    });
}

/*
signout() is called from the account.html page, "Sign Out" links onclick attribute.
Clears the localStorage and expires the public cookie.
Initiates asynchronous POST to /api/users/logout
If request is fulfilled{
  log response and redirect to the login.html page
}
Else If request failed{
  log error 
}
*/
async function signout() {
  let controllerUrl = "http://localhost:8080/api/users/logout";
  localStorage.clear();
  document.cookie =
    "logIn=loggedIn; expires=Thu, 18 Dec 2013 12:00:00 UTC; path=/";

  await fetch(controllerUrl, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      console.log(response);
      setTimeout(function () {
        window.location.href = "login.html";
      }, 2000);
    })
    .catch((error) => {
      console.log(`error logging out: ${error}`);
      window.location.href = "login.html";
    });
}
//==========END LOGIN AND LOGOUT==========

//==========START FORGOT PASSWORD===========
function forgotPassword() {
  postEmail();
}
function resetForgotPassword() {}
function resetPassword() {
  if (
    document.getElementById("resetPass").value ===
    document.getElementById("resetPassConfirm").value
  ) {
    postNewPassword();
  }
}
async function postNewPassword() {
  const controllerUrl = "http://localhost:8080/api/users/reset-password";
  const email1 = localStorage.getItem("email");
  const oldPass = document.getElementById("oldPass").value;
  let encodedData = "Basic " + window.btoa(`${email1}:${oldPass}`);
  const password = document.getElementById("resetPass").value;
  const newPassword = btoa(password);

  await fetch(controllerUrl, {
    method: "POST",
    credentials: "include",
    headers: {
      Authorization: encodedData,
    },
    body: newPassword,
  }).then((response) => {
    if (response.status > 199 && response.status < 206) {
      //good response
      setTimeout(function () {
        window.location.href = "account.html";
      }, 1000);
    } else {
      refresh(postNewPassword);
    }
  });
}

async function postEmail() {
  const controllerUrl = "http://localhost:8080/api/users/forgot-password";
  const email = document.getElementById("forgotPasswordEmail").value;

  await fetch(controllerUrl, {
    method: "POST",
    headers: {
      "Content-Type": "text/html",
    },
    body: email,
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.msg === "email sent") {
        window.alert(
          `Please check your email, ${email} to reset your password.`
        );
        window.location.href = "forgotpasswordreset.html";
      }
    })
    .catch((error) => {
      console.log("Error in postEmail(), response not fulfilled");
    });
}

// ========== END FORGOT PASSWORD ==========

// ========== START EDIT ACCOUNT ==========
function onloadAccount() {
  if (document.cookie.length === 0) {
    window.location.href = "unloggedaccount.html";
  }
  //else stay on account page (do nothing)
}
//EDIT ACCOUNT API - FRONT END

async function updateAddress() {
  const controllerUrl = "NEED ENDPOINT";
  const email1 = localStorage.getItem("email");
  let address1 = {
    houseNumber: document.getElementById("addressNumber").value,
    street: document.getElementById("addressText").value,
    city: document.getElementById("addressCity").value,
    state: document.getElementById("addressState").value,
    postalCode: document.getElementById("addressZipcode").value,
    country: "USA",
  };

  await fetch(controllerUrl, {
    method: "POST",
    headers: {
      "Content-Type": "text/html",
    },
    body: [{ email: email1 }, { address: address1 }],
  })
    .then((response) =>
      response.json().then((data) => ({ status: response.status, body: data }))
    )
    .then((obj) => {
      if (obj.status > 199 && obj.status <= 205) {
        window.alert("Address succesfully updated!");
        window.location.href = "account.html";
      } else {
        refresh(updateAddress);
      }
    })
    .catch((error) => {
      console.log("PostAddress() error: " + error);
    });
}

async function postPhoneNumber() {
  const controllerUrl = "NEED ENDPOINT";
  const email1 = localStorage.getItem("email");
  let phone1 = document.getElementById("editPhoneNumber").value;

  await fetch(controllerUrl, {
    method: "POST",
    headers: {
      "Content-Type": "text/html",
    },
    body: [{ email: email1 }, { phoneNumber: phoneNum }],
  })
    .then((response) =>
      response.json().then((data) => ({ status: response.status, body: data }))
    )
    .then((obj) => {
      if (obj.status > 199 && obj.status < 206) {
        //good status
      } else {
        refresh(postPhoneNumber);
      }
    });
}

// ========== END UPDATE ACCOUNT==========

// ========== COOKIE REFRESH - STILL BETA - NOT WORKING ==========
/*
refresh() is called from the .catch() of all async functions which include credentials(cookies).
From the .catch(), refresh() checks to see if the reason the request failed was because the tokens were expired.
It does this by initiating a new asynchronous request to /api/users/refresh
If the request is fulfilled{
  If the status is good{
    log that tokens were refreshed and call the original async function that failed and called refresh() in the first place. 
    Since tokens were refreshed, this second call should be succesful unless there is some other error.
  }
  Else if status is bad{
    Tokens could not be refreshed, likely because the refresh token was expired or invalid.
    Clear storage and cookies and redirect user to login.html
  }
}
Else if the request is not fulfilled{
  Tokens could not be refreshed
  Clear storage and cookies and redirect user to login.html
}
*/
async function refresh(asyncFunction) {
  await fetch("http://localhost:8080/api/users/refresh", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "text/html",
    },
  })
    .then((response) => {
      if (response.status > 199 && response.status <= 205) {
        //good status - tokens were refreshed - go back to original request
        console.log("*Tokens refreshed*");

        asyncFunction(); //recalls the original request with refreshed tokens
      } else {
        console.log("Tokens could not be refreshed");
        localStorage.clear();
        window.cookie.clear();
        window.location.href = "login.html";
      }
    })
    .catch((error) => {
      console.log(error);
      console.log("Error refreshing tokens... redirecting to login");
      localStorage.clear();
      window.cookie.clear();
      window.location.href = "login.html";
    });
}

//========= END COOKIE REFRESH ==========
