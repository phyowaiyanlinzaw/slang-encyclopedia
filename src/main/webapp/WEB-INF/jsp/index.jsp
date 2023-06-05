<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="preconnect" href="https://fonts.googleapis.com" />
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
		<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer"/>
		<title>Home</title>
		<style>
			* {
        			margin: 0;
        			padding: 0;
        			box-sizing: border-box;
      		}
      		body {
        			font-family: "Roboto:wght@700", sans-serif;
        			background-color: #10151b;
      		}
      		.container {
        			width: 100%;
        			height: 100vh;
        			display: flex;
        			flex-direction: column;
        			justify-content: space-between;
        			align-items: center;
      		}
      		.header {
        width: 100%;
        height: 10vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: #ffffff;
        margin-top: 70px;
      }
      .logo-name {
        font-size: 4rem;
        font-weight: 700;
      }
      .description-logo {
        font-size: 1rem;
        font-weight: 300;
        color:#EFFE00
      }
      .section {
        width: 100%;
        height: 80vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }
      .logo-pic {
        width: 100%;
        height: 200px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;

      }
      .search-section {
        width: 500px;
        height: 50px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 60px;
      }
      .search-bar {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 1px solid #ffffff;
        float: left;

      }
      .search-bar input {
        width: 100%;
        height: 100%;
        color: #ffffff;
        border: none;
        outline: none;
        background-color: #10151b;
        font-size: 1rem;
        font-weight: 300;
        padding-left: 10px;
      }
      .search-bar input:focus {
        border: 1px solid #1fa2f2;

      }


      .search-button-suffix {
        width: 10%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        float: right;
        background-color: #1fa2f2;
      }
      .search-button-suffix a {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
      }
      .search-button-suffix a i {
        font-size: 1rem;
        color: #ffffff;
      }
      .search-button-suffix a:hover {
        background-color: #04ab38;
      }
      .footer {
        width: 100%;
        height: 10vh;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #ffffff;
        font-size: 0.8rem;
        font-weight: 300;
      }
      .contact-icons {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      .contact-icons a {
        width: 30px;
        height: 30px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0 10px;
        text-decoration: none;
      }
      .contact-icons a i {
        font-size: 1.5rem;
        color: #ffffff;
      }
      .contact-icons a:hover {
        color: #1fa2f2;
      }
      .contact-icons a:hover i {
        color: #1fa2f2;
      }
		</style>
	</head>
	<body>
		<div class="container">
      		<header>
        			<div class="header">
          			<div class="logo-name">SLANGz</div>
          			<div class="description-logo">Collection of Modern Slang Words</div>
        			</div>
      		</header>
      
      <section>
        <div class="logo-pic">
          <img src="/logo.png" alt="" />
        </div>
        <div class="search-section">
          <div class="search-bar">
            <input type="text" placeholder="Search" />
          </div>
          <div class="search-button-suffix">
            <a href="showSearchedWords.html">
              <i class="fa-sharp fa-solid fa-magnifying-glass"></i>
            </a>
          </div>
        </div>
      </section>
      
      <footer>
        <div class="footer">
          <div class="contact-icons">
            <a href="https://www.facebook.com/" title="facebook">
              <i class="fab fa-facebook-f"></i>
            </a>
            <a href="https://www.instagram.com/" title="instagram">
              <i class="fab fa-instagram"></i>
            </a>
            <a href="https://www.twitter.com/" title="twitter">
              <i class="fa-brands fa-github"></i>
            </a>
            <a href="https://www.linkedin.com/" title="linkedin">
              <i class="fab fa-linkedin-in"></i>
            </a>
          </div>
        </div>
    		</footer>
    		
    </div>
	</body>
</html>