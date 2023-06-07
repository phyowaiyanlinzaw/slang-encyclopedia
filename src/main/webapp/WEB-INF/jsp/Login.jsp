<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://www.springframework.org/tags/form"
prefix="form"%>
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
    <title>LOG IN</title>
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
      .register-p {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 1rem;
      }
      .register-p p {
        font-size: 0.8rem;
        font-weight: 500;
        color: #fff;
      }
      .register-p p a {
        color: #fff;
        text-decoration: none;
      }
      .register-p p a:hover {
        text-decoration: underline;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <header>LOG IN</header>
      <section>
        <div class="log-in-form">
          <form>
            <div class="input-container">
              <i class="fas fa-envelope"></i>
              <input type="email" title="Email" placeholder="Email" />
            </div>
            <div class="input-container">
              <i class="fas fa-lock"></i>
              <input type="password" title="Password" placeholder="Password" />
            </div>
            <button type="submit">LOG IN</button>
          </form>
          <div class="register-p">
            <p>Don't have an account?<a> Register</a></p>
          </div>
        </div>
      </section>
    </div>
  </body>
</html>
