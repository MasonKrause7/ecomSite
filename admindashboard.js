async function getCustomers() {
  const controllerUrl = "http://localhost:8080/api/admin/get_users";
  await fetch(controllerUrl, {
    method: "GET",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      //NEED TO RENDER THIS INFORMATION IN A USER FRIENDLY WAY
    });
}

function addEmployee() {
  let controllerUrl = "http://localhost:8080/api/admin/add_employee";
  let employeeObj = getEmployeeObj();
  let JSONemployeeObj = JSON.stringify(employeeObj);

  postNewEmployee(controllerUrl, JSONemployeeObj);
}
async function postNewEmployee(controllerUrl, employee) {
  await fetch(controllerUrl, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
    body: employee,
  }).then((response) => {
    console.log(response);
    if (response.status > 199 && response.status < 206) {
      window.location.href = "admindashboard.html";
    } else if (response === 401) {
      refresh(addEmployee);
    }
  });
}
function getEmployeeObj() {
  if (
    document.getElementById("employeeNewPass").value !=
    document.getElementById("employeeConfirmNewPass").value
  ) {
    document.getElementById("employeeHiddenMessage").innerText =
      "Password must match confirmation, please double check passwords.";
  } else {
    document.getElementById("employeeHiddenMessage").innerText = "";
    var employeeObj = {
      firstName: document.querySelector("#employeeFirstName").value,
      lastName: document.getElementById("employeeLastName").value,
      email: document.getElementById("employeeEmail").value,
      password: document.getElementById("employeeNewPass").value,
      phoneNumber: document.getElementById("employeePhone").value,
      address: [
        {
          houseNumber: document.getElementById("employeeHouseNumber").value,
          street: document.getElementById("employeeStreetName").value,
          city: document.getElementById("employeeCity").value,
          state: document.getElementById("employeeState").value,
          postalCode: document.getElementById("employeeZipcode").value,
          country: "USA",
        },
      ],
      authority: "EMPLOYEE",
    };
  }
  return employeeObj;
}

async function getEmployees() {
  const controllerUrl = "http://localhost:8080/api/admin/get_employees";
  var modal = document.getElementById("modal-5");
  var teamList = "";
  document.getElementById("teamList").innerText = "";
  await fetch(controllerUrl, {
    method: "GET",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) =>
      response.json().then((data) => ({ status: response.status, body: data }))
    )
    .then((obj) => {
      if (obj.body[0] != null) {
        for (var i = 0; i < obj.body.length; i++) {
          var teamMemberLink =
            "http://localhost:8080/api/admin/get_employee/" +
            obj.body[i].userId;
          var a = document.createElement("a");
          //var linkName = //NOT FINISHED!!!! make it so  when the list of employees is displayed, they can click on one to view the full employee information
          teamList +=
            "\n" +
            obj.body[i].userId +
            ": " +
            obj.body[i].firstName +
            " " +
            obj.body[i].lastName +
            " - " +
            obj.body[i].email +
            " - " +
            obj.body[i].authority;
        }
        document.getElementById("teamList").innerText = teamList;
      } else {
        document.getElementById("teamList").innerText =
          "No admin or employees to show...";
      }
    })
    .catch((error) => {
      console.log("getEmployees() error: " + error);
      refresh(getEmployees);
    });
}
async function removeEmployee() {
  const employeeID = document.getElementById("employeeID").value;
  const controllerURL =
    "http://localhost:8080/api/admin/delete_employee/" + employeeID;
  await fetch(controllerURL, {
    method: "DELETE",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      console.log(response);
      if (response.status > 199 && response.status < 206) {
        window.location.href = "admindashboard.html";
      }
    })
    .catch((error) => {
      console.log("Error removing employee: " + error);
      refresh(removeEmployee);
    });
}
function closeModal(modalName) {
  var modal = document.getElementById(modalName);
  modal.style.display = "none";
}
