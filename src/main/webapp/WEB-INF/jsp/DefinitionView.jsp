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
        width: 10%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-left: 5rem;
      }
      .logo img {
        width: 50px;
        height: 50px;
      }
      .logo h {
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
        padding: 0 1rem;
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
        width: 100%;
        height: 100%;
        border: none;
        outline: none;
        color: #ffffff;
        font-size: 1.2rem;
        background-color: #10151b;
        margin-left: 1rem;
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
      .inline-icons input:-webkit-autofill {
        -webkit-box-shadow: 0 0 0 30px #10151b inset;
        -webkit-text-fill-color: #ffffff;
      }
      .inline-icons i:hover {
        color: #ffffff;
        cursor: pointer;
      }
      .upload-and-user {
        width: 20%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: space-around;
        margin-left: 10rem;
      }
      .upload-and-user button {
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
      .upload-and-user button:hover {
        color: #04ab38;
        cursor: pointer;
      }
      .upload-and-user button i {
        font-size: 2rem;
      }
      .upload-and-user button i:hover {
        color: #04ab38;
      }

      .section {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
      }
      .searched-word {
        width: 80vw;
        height: 30%;
        display: flex;
        background-color: #1b2936;
        margin-top: 20px;
        flex-direction: column;
        border-radius: 10px;
        padding: 30px;
      }
      .searched-word h1 {
        color: #134fe5;
        font-size: 3rem;
        margin-bottom: 1rem;
      }
      .def {
        color: #ffffff;
        font-size: 1.2rem;
        font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
        margin: 1rem 0;
      }
      .eg {
        color: #ffffff;
        font-size: 1.2rem;
        font-style: italic;
        margin: 1rem 0;
      }
      .info {
        color: #ffffff;
        font-size: 1.2rem;
        font-style: bold;
        margin: 1rem 0;
      }
      .vote {
        color: #ffffff;
        font-size: 1.2rem;
        font-style: bold;
        display: flex;
        margin: 1rem 0;
      }
      .upvote {
        color: #04ab38;
        font-size: 1.2rem;
        font-style: bold;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid #04ab38;
        border-radius: 5px;
        margin-right: 1rem;
        padding: 0 1rem;
      }
      .downvote {
        color: #ff0000;
        font-size: 1.2rem;
        font-style: bold;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid #ff0000;
        border-radius: 5px;
        margin-right: 1rem;
        padding: 0 1rem;
      }

      .upvote:hover {
        cursor: pointer;
        background-color: #04ab38;
        color: #ffffff;
      }

      .downvote:hover {
        cursor: pointer;
        background-color: #ff0000;
        color: #ffffff;
      }

      @media (max-width: 768px) {
        .container {
          padding: 0.5rem;
        }
        .navbar {
          height: 100%;
          border-radius: 10px;
        }
        .logo h1 {
          display: none;
        }
        .logo img {
          width: 50px;
          height: 50px;
        }
        .search-bar {
          margin-left: 0.5rem;
          width: 90%;
        }

        .upload-and-user {
          margin-left: 0.5rem;
          display: flex;
          justify-content: space-around;
          align-items: center;
          width: 30%;
        }

        .upload-and-user button {
          font-size: 1.5rem;
          width: 40px;
          height: 40px;
        }

        .upload-and-user button i {
          font-size: 1.5rem;
        }
        .searched-word {
          padding: 20px;
        }

        .searched-word h1 {
          font-size: 2rem;
        }

        .def,
        .eg,
        .info,
        .vote {
          font-size: 1rem;
        }

        .upvote,
        .downvote {
          font-size: 1rem;
        }

        .upvote,
        .downvote {
          margin-right: 0.5rem;
          padding: 0 0.5rem;
        }

        .upvote i,
        .downvote i {
          font-size: 1rem;
        }

        .upvote:hover {
          background-color: #04ab38;
          color: #ffffff;
          cursor: pointer;
        }

        .downvote:hover {
          background-color: #ff0000;
          color: #ffffff;
          cursor: pointer;
        }
      }
    </style>
  </head>
  <body>
    <div class="container">
      <header>
        <div class="navbar">
          <div class="logo">
            <h1>SLANGz</h1>

            <img
              src="<%=request.getContextPath()%>/resource/logo.png"
              alt="logo"
            />
          </div>

          <div class="search-bar">
            <form class="inline">
              <div class="inline-icons">
                <i class="fas fa-search"></i>
                <input
                  type="text"
                  name="search"
                  id="search"
                  placeholder="Search for a word"
                />
                <a href="/SlangEncyclopedia/Shuffle"><i class="fas fa-shuffle"></i></a>
              </div>
            </form>
            <div class="upload-and-user">
              <a href="/SlangEncyclopedia/UploadForm" title="to upload">
                <button title="upload" type="button" id="upload-btn">
                  <i class="fas fa-plus"></i>
                </button>
              </a>

              <a href="/SlangEncyclopedia/Dashboard">
                <button title="user" type="button" id="reg-btn">
                  <i class="fas fa-user"></i></button
              ></a>
            </div>
          </div>
        </div>
      </header>
		<c:forEach items="${defList}" var="def">
        	<div class="searched-word card">
        		<div class="word">
                	<h1>${def.term }</h1>
                	<p class = "def"> ${def.definition_text}</p>
					<p class="info"> By ${def.createdBy}  ${def.createdDate }</p>
          		</div>
          		<div class="vote">
            		<div class="upvote"  data-definitionId="${def.defId}">
              				👍
              			<p>${def.likeCount}</p>
            		</div>
            		<div class="downvote" data-definitionId="${def.defId}">
              				👎
              			<p>${def.dislikeCount}</p>
            		</div>
          		</div>
    		</div>
      	            </c:forEach>
	</body>
  <script>
    // Change Placeholder
    const mediaQuery = window.matchMedia("(max-width: 768px)");
    const searchPlaceHolder = document.getElementById("search");
    const form = document.querySelector("inline");
    const searchIcon = document.querySelector(".fa-search");
    const likeButtonAll = document.querySelectorAll(".upvote");
    const dislikeButtonAll = document.querySelectorAll(".downvote");
    const likeButton = document.querySelector(".upvote");
    const dislikeButton = document.querySelector(".downvote");
    const isLoggedIn = "${isLoggedIn}";

    likeButtonAll.forEach((likeButton) => {
      likeButton.addEventListener("click", () => {
        if(isLoggedIn=="logged in"){
          updateLikeFunction(likeButton);
        }
        else{
          window.location.href="/SlangEncyclopedia/Login"
        }
        
      });
    });
    
    dislikeButtonAll.forEach((dislikeButton) => {
        dislikeButton.addEventListener("click", () => {
          if(isLoggedIn=="logged in"){
            updateDislikeFunction(dislikeButton);
          }
          else{
            window.location.href="/SlangEncyclopedia/Login"
          }
          
        });
        
    });

    function updateLikeFunction(likeButton) {
    	  const definitionId = likeButton.getAttribute("data-definitionId");
    	  const likeCount = likeButton.querySelector("p").textContent;
    	  console.log("definitionId:", definitionId);
    	  console.log("likeCount:", likeCount);
    	  
    	  const likeCountInt = parseInt(likeCount);
    	  const likeCountNew = likeCountInt + 1;
    	  likeButton.querySelector("p").textContent = likeCountNew;
    	  
    	  const xhr = new XMLHttpRequest();
    	  xhr.open("POST", "/SlangEncyclopedia/UpdateLike", true);
    	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    	  xhr.send("definitionId=" + definitionId);

    	  xhr.onreadystatechange = function () {
    	    if (this.readyState == 4 && this.status == 200) {
    	    }
    	  };
    	}

    function updateDislikeFunction(dislikeButton) {
    	  const definitionId = dislikeButton.getAttribute("data-definitionId");
    	  const dislikeCount = dislikeButton.querySelector("p").textContent;
    	  console.log("definitionId:", definitionId);
    	  console.log("dislikeCount:", dislikeCount);
    	  
    	  const dislikeCountInt = parseInt(dislikeCount);
    	  const dislikeCountNew = dislikeCountInt + 1;
    	  dislikeButton.querySelector("p").textContent = dislikeCountNew;
    	  
    	  const xhr = new XMLHttpRequest();
    	  xhr.open("POST", "/SlangEncyclopedia/UpdateDislike", true);
    	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    	  xhr.send("definitionId=" + definitionId);

    	  xhr.onreadystatechange = function () {
    	    if (this.readyState == 4 && this.status == 200) {
    	    }
    	  };
    }


    searchIcon.addEventListener("click", () => {
      form.submit();
    });

    if (mediaQuery.matches) {
      // Change Placeholder
      searchPlaceHolder.placeholder = "Search";
    } else {
      searchPlaceHolder.placeholder = "Search for a word";
    }
  </script>
</html>
