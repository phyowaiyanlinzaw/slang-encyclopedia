<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form"
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
    <title>User Profile</title>
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
      .avatar {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        background-color: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 2rem;
        font-weight: 700;
        color: #10151b;
      }
        .avatar i {
            color: #10151b;
        }
        .name-and-verification{
            width: 100%;
            max-width: 800px;
            margin-top: 2rem;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .name-and-verification h1{
            font-size: 1.5rem;
            font-weight: 700;
            color: #fff;
        }
        .name-and-verification .verified{
            font-size: 1rem;
            font-weight: 700;
            color: #fff;
            background-color: #1e2a3a;
            padding: 0.5rem 1rem;
            border-radius: 10px;
        }
        .name-and-verification i{
            font-size: 1.5rem;
            color: #fff;
            margin-left: 1rem;
        }
      .options {
        width: 100%;
        max-width: 800px;
        margin-top: 2rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .btn {
        width: 100%;
        padding: 1rem;
        border-radius: 10px;
        font-size: 1rem;
        font-weight: 700;
        color: #fff;
        cursor: pointer;
        margin: 0 0.5rem;
      }
      .info-btn,
      .def-btn,
      .edit-btn,
      .logout-btn {
        background-color: #1e2a3a;
        border: none;
        outline: none;
      }
      .card {
        width: 100%;
        max-width: 800px;
        margin-top: 2rem;
        background-color: #1e2a3a;
        border-radius: 10px;
        overflow: hidden;
      }
      .card-body{
        padding: 1rem;
      }
        .user-info-item{
            margin-bottom: 1rem;
        }
        .user-info-item h3{
            font-size: 1rem;
            font-weight: 700;
            color: #fff;
        }
        .user-info-item p{
            font-size: 1rem;
            font-weight: 500;
            color: #fff;
        }


      .btn.selected{
        background-color: #fff;
        color: #10151b;
      }
      table{
        width: 100%;
        border-collapse: collapse;
        margin-top: 1rem;
      }
        th{
            font-size: 1rem;
            font-weight: 700;
            color: #fff;
            text-align: center;
            padding: 0.5rem 0;
            
        }
        td{
            font-size: 1rem;
            font-weight: 500;
            color: #fff;
            padding: 0.5rem 0;
            text-align: center;
        }

        .hidden{
            display: none;
        }

        .edit-profile input{
            width: 100%;
            padding: 1rem;
            border-radius: 10px;
            border: none;
            outline: none;
            background-color: #1e2a3a;
            color: #fff;
            font-size: 1rem;
            font-weight: 500;
            border-bottom: #fff 1px solid;
        }
        .edit-profile button{
            width: 100%;
            padding: 1rem;
            border-radius: 10px;
            border: none;
            outline: none;
            background-color: #fff;
            color: #10151b;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            text-align: center;
            margin-top: 1rem;
        }

        .edit-profile button:hover{
            background-color: #10151b;
            color: #fff;
        }

        .log-out{
            width: 100%;
            padding: 1rem;
            border-radius: 10px;
            border: none;
            outline: none;
            background-color: #10151b;
            color: #fff;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            text-align: center;

        }


        .confirmation-btns button{
            width: 80%;
            padding: 1rem;
            border-radius: 10px;
            border: #fff 1px solid;
            outline: none;
            background-color: #10151b;
            color: #fff;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            text-align: center;
            margin: 0 0.5rem;

        }

        .yes-btn:hover{
            background-color: #fff;
            color: #10151b;
        }

        .no-btn:hover{
            background-color: #fff;
            color: #10151b;
        }

        .back-home{
            width: 10%;
            padding: 1rem;
            border-radius: 10px;
            border: #fff 1px solid;
            outline: none;
            background-color: #10151b;
            color: #fff;
            font-size: 0.8rem;
            font-weight: 700;
            cursor: pointer;
            text-align: center;
            margin-top: 3rem;
        }

        .back-home:hover{
            background-color: #fff;
            color: #10151b;
        }

    </style>
  </head>
  <body>
    <div class="container">
      <div class="avatar">{res.username.charAt(0).toUpperCase()}</div>
      <div class="name-and-verification">
        <h1>{res.username}</h1>
        <i class="fas fa-check-circle"></i>
      </div>
    
      <div class="options">
        <button class="btn info-btn selected">User Info</button>
        <button class="btn edit-btn">Edit Profile</button>

        <button class="btn logout-btn">Log Out</button>
      </div>
      <div class="card user-info">
        <div class="card-header">
        </div>
        <div class="card-body">
          <div class="user-info-item">
            <table>
                <th>Username</th>
                <th>Email</th>
                <th>Likes Total</th>
                <th>Dislikes Total</th>
                <th>Definitions Amount</th>
                <tr>
                    <td>{res.username}</td>
                    <td> {res.email} </td>
                    <td> 0 </td>
                    <td> 0 </td>
                    <td> 0 </td>
                </tr>
            </table>
          </div>
      </div>
    </div>
    <div class="card edit-profile hidden">
        <div class="card-header">
        </div>
        <div class="card-body">
            <div class="user-info-item">
            <input type="text" placeholder="Username" />
            </div>
            <div class="user-info-item">
            <input type="email" placeholder="Email" />
            </div>
            <div class="user-info-item">
            <input type="password" placeholder="Password" />
            </div>
            <div class="user-info-item">
            <input type="password" placeholder="Confirm Password" />
            </div>
            <div class="user-info-item">
            <button class="btn">Save</button>
            </div>
        </div>
    </div>
    <div class="card log-out hidden">
        <div class="card-header"></div>
        <div class="card-body">
            <div class="user-info-item">
                <h3>Are you sure you want to log out?</h3>
            </div>
            <div class="confirmation-btns">
                <div class="user-info-item">
                    <button class="yes-btn">Yes</button>
                </div>
                <div class="user-info-item">
                    <button class="no-btn">No</button>
                </div>
            </div>
            
        </div>
    </div>
    <div class="card uploaded-def hidden">

    </div>

    <div class="back-home">
        <i class="fas fa-arrow-left"></i>
        <p>Home</p>
    </div>
    </div>
  </body>
  <script>
    const infoBtn = document.querySelector(".info-btn");
    const editBtn = document.querySelector(".edit-btn");
    const logoutBtn = document.querySelector(".logout-btn");
    const userInfo = document.querySelector(".user-info");
    const editProfile = document.querySelector(".edit-profile");
    const logOut = document.querySelector(".log-out");
    const backHomeBtn = document.querySelector(".back-home");

    infoBtn.addEventListener("click", () => {
      infoBtn.classList.add("selected");
      editBtn.classList.remove("selected");
      logoutBtn.classList.remove("selected");
      userInfo.style.display = "block";

      editProfile.style.display = "none";
      logOut.style.display = "none";

    });
    editBtn.addEventListener("click", () => {
      infoBtn.classList.remove("selected");
      editBtn.classList.add("selected");
      logoutBtn.classList.remove("selected");
      editProfile.style.display = "block";
      userInfo.style.display = "none";
        logOut.style.display = "none";

    });
    logoutBtn.addEventListener("click", () => {
      infoBtn.classList.remove("selected");
      editBtn.classList.remove("selected");
      logoutBtn.classList.add("selected");
      userInfo.style.display = "none";
      editProfile.style.display = "none";
      logOut.style.display = "block";

    });

    backHomeBtn.addEventListener("click", () => {
        window.location.href = "showSearchedWords.html";
    });
  </script>
</html>
