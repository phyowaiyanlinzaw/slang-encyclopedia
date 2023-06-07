<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
    <title>SLANGz</title>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      body {
        font-family: "Poppins:wght@500", sans-serif;
        background-color: #10151b;
      }
      .container {
        width: 100%;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
      }
      .navbar {
        width: 100vw;
        height: 100%;
        display: flex;

        align-items: center;
        color: #ffffff;
        background-color: #1b2936;
      }
      .logo {
        width: 20%;
        height: 100%;
        display: flex;
        margin-left: 10rem;
        align-items: center;
      }
      .logo img {
        width: 50px;
        height: 50px;
      }
        .logo h{
            font-size: 2rem;
            margin-left: 1rem;
        }
      .logo:hover {
        cursor: pointer;
      }
      .search-bar {
        width: 100%;
        height: 100px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-left: 10rem;
      }
      .inline {
        display: inline-flex;
        align-items: center;
        border-radius: 5px;
        width: 50%;
        height: 50%;
        background-color: #10151b;
      }
      .inline-icons {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: space-around;
        align-items: center;
      }
      .inline-icons i {
        font-size: 1.2rem;
        color: #ffffff;
      }
      .inline-icons input {
        width: 80%;
        height: 100%;
        border: none;
        outline: none;
        color: #ffffff;
        font-size: 1.2rem;
        background-color: #10151b;
      }
      .inline-icons input::placeholder {
        color: #767676;
        font-size: 1.2rem;
      }
      .inline-icons input:focus {
        color: #ffffff;
      }
      .inline-icons input:active {
        color: #ffffff;
      }
      .inline-icons input:hover {
        color: #ffffff;
      }
      .inline-icons i:hover {
        color: #ffffff;
        cursor: pointer;
      }
      .upload-and-user{
        width: 20%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: space-around;
        margin-left: 10rem;
      }
        .upload-and-user button{
            width: 50px;
            height: 50px;
            border-radius: 50%;
            outline: none;
            background-color: #134fe5;
            color: #ffffff;
            font-size: 2rem;
            border: none;
            margin: 0 1rem;

        }
        .upload-and-user button:hover{
            color: #04AB38;
            cursor: pointer;
        }
        .upload-and-user button i{
            font-size: 2rem;
        }
        .upload-and-user button i:hover{
            color: #04AB38;
        }
        .modal{
          display: none;/* Hidden by default */
          position: fixed;/* Stay in place */
          z-index: 1;/* Sit on top */
          left: 0;/* Full width */
          top: 0;/* Full height */
          height: 100%;/* Full height */
          width: 100%;/* Full width */
          overflow: auto;/* Enable scroll if needed */
          background-color: rgba(0, 0, 0, 0.7);
        }
        .modal-content{
          background-color: #1b2936;
          margin: 15% auto;/* 15% from the top and centered */
          padding: 20px;
          border-radius: 10px;
          width: 80%;/* Could be more or less, depending on screen size */
        }
        .close{
          color: #aaaaaa;
          float: right;
          font-size: 28px;
          font-weight: bold;
        }
        .close:hover,
        .close:focus{
          color: #000;
          text-decoration: none;
          cursor: pointer;
        }
        .modal input{
          width: 100%;
          height: 50px;
          border-radius: 5px;
          outline: none;
          border: none;
          background-color: #10151b;
          color: #ffffff;
          font-size: 1.2rem;
          padding: 0 1rem;
          margin-top: 1rem;
        }
        .modal input::placeholder{
          color: #767676;
          font-size: 1.2rem;
        }
        .modal input:focus{
          color: #ffffff;
        }
        .modal input:active{
          color: #ffffff;
        }
        .modal input:hover{
          color: #ffffff;
        }
        .modal button{
          width: 100%;
          height: 50px;
          border-radius: 5px;
          outline: none;
          border: none;
          background-color: #134fe5;
          color: #ffffff;
          font-size: 1.2rem;
          padding: 0 1rem;
          margin-top: 1rem;
        }
        .modal button:hover{
          color: #04AB38;
          cursor: pointer;
        }
        .modal h2{
          color: #ffffff;
          font-size: 1.2rem;
          margin-bottom: 1rem;
          text-align: center;
        }
        .register{
          width: 100%;
          height: 100%;
          display: flex;
          align-items: center;
          justify-content: center;
          background-color: #1b2936;
          margin-top: 20px;
        }
        .login{
          width: 100%;
          height: 100%;
          display: flex;
          align-items: center;
          justify-content: center;
          background-color: #1b2936;
          margin-top: 20px;
        }
        .register a{
          color: #ffffff;
          font-size: 1.2rem;
          text-decoration: none;
        }
        .register a:hover{
          color: #04AB38;
          cursor: pointer;
        }
        .register p{
          color: #ffffff;
          font-size: 1.2rem;
        }
        .login a{
          color: #ffffff;
          font-size: 1.2rem;
          text-decoration: none;
        }
        .login a:hover{
          color: #04AB38;
          cursor: pointer;
        }
        .login p{
          color: #ffffff;
          font-size: 1.2rem;
        }
        .searched-word{
          width: 70vw;
          height: 100%;
          display: flex;
          align-items: center;
          justify-content: center;
          background-color: #1b2936;
          margin-top: 20px;
          flex-direction: column;
          border-radius: 10px;
          padding: 30px;
        }
        .searched-word h1{
          color: #134fe5;
          font-size: 3rem;
        }
        .def{
          color: #ffffff;
          font-size: 1.2rem;
          font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
        }
        .eg{
          color: #ffffff;
          font-size: 1.2rem;
          font-style: italic;
        }
        .info{
          color: #ffffff;
          font-size: 1.2rem;
          font-style: bold;
        }
        .vote{
          color: #ffffff;
          font-size: 1.2rem;
          font-style: bold;
          display: flex;
          
        }
        .upvote{
          color: #04AB38;
          font-size: 1.2rem;
          font-style: bold;
          display: flex;
          align-items: center;
          justify-content: center;
          border: 1px solid #04AB38;
          border-radius: 5px;
        }
        .downvote{
          color: #ff0000;
          font-size: 1.2rem;
          font-style: bold;
          display: flex;
          align-items: center;
          justify-content: center;
          border: 1px solid #ff0000;
          border-radius: 5px;
        }
    </style>
  </head>
  <body>
    <div class="container">
      <header>
        <div class="navbar">
            <div class="logo">
                <h1>SLANGz</h1>
              
              <img src="/logo.png" alt="logo" />
            </div>
          
                <div class="search-bar">
                    <form class="inline">
                      <div class="inline-icons">
                        <i class="fas fa-search"></i>
                        <input type="text" name="search" id="search" placeholder="Search for a word" />
                        <i class="fas fa-shuffle"></i>   
                      </div>
                      
                    </form>
                    <div class="upload-and-user" >
                      <a href="uploadform.html" title="to upload">
                        <button title="upload" type="button" id="upload-btn"> 
                          <i class="fas fa-plus"></i>
                        </button>
                      </a>
                      
                        <button title="user"  type = "button" id="reg-btn">
                          <i class="fas fa-user"></i>
                          </button>
                    </div>
            </div>
            
          </div>
          <div id="loginModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Login</h2>
                <form:form action="/SlangEncyclopedia/ProcessLogin" method="POST" modelAttribute="userBean">
                    <form:input type="text" name="email" id="email" placeholder="Email" path="email"/>
                    <form:input type="password" name="password" id="password" placeholder="Password" path="password"/>
                    <div class="submit-btn">
                        <button type="submit" name="login">Login</button>
                    </div>

                </form:form>
                <div class="register">
                    <p>Don't have an account? <a onclick="showRegisterModal()">Register</a></p>
            </div>
        </div>
            
        </div>
        <div id="regModal" class="modal">
          <div class="modal-content">
              <span class="close">&times;</span>
              <h2>Register</h2>
              <form:form action="/SlangEncyclopedia/ProcessRegister" method="POST" modelAttribute="userBean">
                  <form:input type="text" name="username" id="username" placeholder="Username" path="username"/>
                  <form:input type="text" name="email" id="email" placeholder="Email" path="email"/>
                  <form:input type="password" name="password" id="password" placeholder="Password" path="password"/>
                  <form:input type="password" name="password" id="password" placeholder="Confirm Password" path="confirm_password"/>
                  <div class="submit-btn">
                    <a onclick="showOtpModal()">
                      <button id="register-modal-button" type="button" name="register">Register</button>
                    </a>
                      
                  </div>

              </form:form>
              <div class="login">
                  <p>Already have an account? <a onclick="showLoginModal()">Login</a></p>
              </div>
          </div>
        </div>
        <div id="otpModal" class="modal">
          <div class="modal-content">
            <span class="close">&times;</span>
              <h2>Enter Your OTP</h2>
              <form action="" method="">
                <input type="text" name="otp" id="otp" placeholder="Enter OTP">
                <div class="submit-btn">
                  <button type="submit" name="verify">Verify</button>
                </div>
              </form>
          </div>
        </div>
      </header>
      <section>
        <div class="searched-word card">
            <div class="word">
                <h1>Word</h1>
                <p class="def">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ea optio, ad, officiis eos fugiat quo aut laudantium explicabo placeat saepe repellendus fuga deleniti! Cupiditate, corporis. Ea recusandae animi iure saepe.</p>
                <p class="eg">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Doloremque commodi exercitationem ullam laudantium dolorem </p>
                <p class="info">
                  by Someone on 12/12/2020
                </p>
            </div>
            <div class="vote">
                <div class="upvote">
                    ð
                    <p>100</p>
                </div>
                <div class="downvote">
                    ð 
                    <p>100</p>
                </div>
            </div>
        </div>
      </section>
      </div>
      
    
  </body>
  <script>

    // Get the modal
    var logInModal = document.getElementById("loginModal");
    var regBtn = document.getElementById("reg-btn");
    var allCloseModalBtn = document.getElementsByClassName("close");
    var regModal = document.getElementById("regModal");
    var otpModal = document.getElementById("otpModal");
    var registerModalBtn = document.getElementById("register-modal-button");

    regBtn.addEventListener("click", function(){
        logInModal.style.display = "block";
    });
      
    // When the user clicks on <span> (x), close the modal
    for(var i = 0; i < allCloseModalBtn.length; i++){
        allCloseModalBtn[i].addEventListener("click", function(){
            logInModal.style.display = "none";
            regModal.style.display = "none";
            otpModal.style.display = "none";
        });
    }

    window.addEventListener("click", function(event){
        if(event.target == logInModal){
            logInModal.style.display = "none";
        }
    });

    function showRegisterModal(){
      logInModal.style.display = "none";
      regModal.style.display = "block";
    }

    function showLoginModal(){
      regModal.style.display = "none";
      logInModal.style.display = "block";
    }

    function showOtpModal(){
      regModal.style.display = "none";
      otpModal.style.display = "block";
    }

  </script>
</html>