<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&Roboto:wght@300;400;500;700&display=swap"
      rel="stylesheet"
    />
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
      integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <title>REGISTER</title>
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      body {
        font-family: "Poppins", sans-serif;
        background-color: #10151b;
      }
      .container {
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
      }
      header {
        font-size: 2rem;
        font-weight: 700;
        text-align: center;
        margin-bottom: 2rem;
        color: #fff;
      }
      section {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      .log-in-form {
        width: 100%;
        max-width: 400px;
        padding: 2rem;
        border-radius: 10px;
        background-color: #1b2936;
      }
      .log-in-form .input-container {
        width: 100%;
        height: 50px;
        margin-bottom: 1.5rem;
        border-radius: 5px;
        background-color: #10151b;
        display: flex;
        align-items: center;
        padding: 0 1rem;
      }
      .log-in-form .input-container i {
        font-size: 1.2rem;
        color: #fff;
        margin-right: 0.5rem;
      }
      .log-in-form .input-container input {
        width: 100%;
        height: 100%;
        background-color: transparent;
        border: none;
        outline: none;
        font-size: 1rem;
        color: #fff;
      }
      .log-in-form button {
        width: 100%;
        height: 50px;
        border-radius: 5px;
        border: none;
        outline: none;
        background-color: #2f80ed;
        color: #fff;
        font-size: 1rem;
        font-weight: 500;
        cursor: pointer;
      }
      .log-in-form button:hover {
        background-color: #2b73c7;
      }
      .log-in-form button:active {
        transform: scale(0.98);
      }
      .log-in-form button:focus {
        background-color: #2b73c7;
      }
      .log-in-p {
        margin-top: 1.5rem;
        text-align: center;
      }
      .log-in-p p {
        font-size: 0.8rem;
        font-weight: 500;
        color: #fff;
      }
      .log-in-p p a {
        color: #fff;
        text-decoration: none;
      }
      .log-in-p p a:hover {
        text-decoration: underline;
      }
      
.input-container {
  position: relative;
}

.toggle-password {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translate(0, -50%);
  cursor: pointer;
  user-select: none;
}
  .toggle-password.show-password::before {
    content: "\f070";
  }
      .error-modal {
        width: 100%;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 1000;
      }

      .error-modal .error-modal-content {
        width: 100%;
        max-width: 500px;
        padding: 2rem;
        border-radius: 0.5rem;
        background-color: #1b2936;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
      }

      .error-modal .error-modal-content .error-modal-header {
        font-size: 2rem;
        font-weight: 700;
        text-align: center;
        margin-bottom: 2rem;
        color: #fff;
      }

      .error-modal .error-modal-content .error-modal-body {
        font-size: 1rem;
        font-weight: 500;
        text-align: center;
        margin-bottom: 2rem;
        color: #fff;
      }

      .error-modal .error-modal-content .error-modal-footer {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .error-modal .error-modal-content .error-modal-footer .btn {
        width: 100%;
        padding: 0.5rem 1rem;
        margin: 0.5rem 0;
        border: 1px solid #333;
        border-radius: 5px;
        outline: none;
        font-size: 1rem;
        font-weight: 500;
        transition: all 0.3s ease;
        cursor: pointer;
      }

      .error-modal .error-modal-content .error-modal-footer .btn:hover {
        background-color: #10151b;
        color: #fff;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <header>REGISTER</header>
      <section>
        <div class="log-in-form">
          <form:form
            action="/SlangEncyclopedia/ProcessRegister"
            method="POST"
            modelAttribute="registerBean"
          >
            <div class="input-container">
              <i class="fas fa-user"></i>
              <form:input
                type="text"
                name="username"
                placeholder="Username"
                path="username"/>
                <form:errors path="username" style="color:red;"></form:errors>
            </div>
            <div class="input-container">
              <i class="fas fa-envelope"></i>
              <form:input
                type="email"
                name="email"
                placeholder="Email"
                path="email"
              />
				<form:errors path="email" style="color:red;"></form:errors>
	            </div>
	            <div class="input-container">
  <form:input
    type="password"
    name="password"
    placeholder="Password"
    path="password"
    id="password"
  />
  <i id="togglePassword1" class="fas fa-eye toggle-password"></i>
  <form:errors path="password" style="color:red;"></form:errors>
</div>
<div class="input-container">
  <form:input
    type="password"
    name="confirm-password"
    placeholder="Confirm Password"
    path="confirm_password"
    id="confirm-password"
  />
  <i id="togglePassword2" class="fas fa-eye toggle-password"></i>
  <form:errors path="confirm_password" style="color:red;"></form:errors>
</div>
            <button type="submit" name="register">REGISTER</button>
            
          </form:form>
          <div class="log-in-p">
            <p>Already have an account?<a href="/SlangEncyclopedia/Login"> Log In</a></p>
          </div>
        </div>
      </section>
    </div>
          <c:if test="${not empty errorMsg }">
        <div class="error-modal">
          <div class="error-modal-content">
            <div class="error-modal-header">
              <h2>Error</h2>
            </div>
            <div class="error-modal-body">
              <p>${errorMsg}</p>
            </div>
            <div class="error-modal-footer">
              <button class="btn solid" id="closeErrorModal">Close</button>
            </div>
          </div>
        </div>
      </c:if>
  </body>
  <script>
  $(document).ready(function () {
    $(".toggle-password").click(function () {
      const input = $(this).siblings("input");
      const type = input.attr("type");

      if (type === "password") {
        input.attr("type", "text");
        $(this).addClass("show-password");
      } else {
        input.attr("type", "password");
        $(this).removeClass("show-password");
      }
    });
  });
  
  const errorModal = document.querySelector(".error-modal");
  const closeErrorModal = document.querySelector("#closeErrorModal");

  closeErrorModal.addEventListener("click", () => {
    errorModal.style.display = "none";
  });
</script>

  
</html>