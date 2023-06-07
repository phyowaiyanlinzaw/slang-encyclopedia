<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://www.springframework.org/tags/form"
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
                path="username"
              />
            </div>
            <div class="input-container">
              <i class="fas fa-envelope"></i>
              <form:input
                type="email"
                name="email"
                placeholder="Email"
                path="email"
              />
            </div>
            <div class="input-container">
              <i class="fas fa-lock"></i>
              <form:input
                type="password"
                name="password"
                placeholder="Password"
                path="password"
              />
            </div>
            <div class="input-container">
              <i class="fas fa-lock"></i>
              <form:input
                type="password"
                name="confirm-password"
                placeholder="Confirm Password"
                path="confirm_password"
              />
            </div>
            <button type="submit" name="register">REGISTER</button>
          </form:form>
          <div class="log-in-p">
            <p>Already have an account?<a> Log In</a></p>
          </div>
        </div>
      </section>
    </div>
  </body>
</html>
