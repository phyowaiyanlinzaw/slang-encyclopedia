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
      href="https://cdnjs.cloudflare.com/ajax/libs font-awesome/6.4.0/css/all.min.css"
      integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <title>Upload New WORD</title>
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      body {
        font-family: "Poppins", sans-serif;
        background-color: #134fe5;
      }
      .container {
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 15px;
      }
      header {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      header h1 {
        font-size: 3rem;
        font-weight: 700;
        color: #fff;
        text-transform: uppercase;
        text-align: center;
      }
      section {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      .upload-form {
        width: 100%;
        max-width: 500px;
        padding: 30px;
        background-color: #fff;
        border-radius: 10px;
      }
      .upload-form p {
        font-size: 0.8rem;
        font-weight: 500;
        color: #000;
        margin-bottom: 15px;
      }
      .upload-form input {
        width: 100%;
        height: 50px;
        margin-bottom: 15px;
        padding: 0 15px;
        border: 1px solid #000;
        border-radius: 5px;
        font-size: 1rem;
        font-weight: 500;
        color: #000;
      }
      .upload-form textarea {
        width: 100%;
        height: 150px;
        margin-bottom: 15px;
        padding: 15px;
        border: 1px solid #000;
        border-radius: 5px;
        font-size: 1rem;
        font-weight: 500;
        color: #000;
      }
      .upload-form input[type="submit"] {
        background-color: #134fe5;
        border: none;
        color: #fff;
        cursor: pointer;
      }
      .upload-form input[type="submit"]:hover {
        background-color: #0e3ac4;
      }
      .upload-form input[type="submit"]:active {
        background-color: #0a2a9e;
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
	<form:form action="/SlangEncyclopedia/ProcessUpload" method="POST" modelAttribute="termandDefBean">    
            <div class="container">
      <header>
        <h1>NEW WORD</h1>
      </header>
      <section>
        <div class="upload-form">
          <p>
            All the definitions on Urban Dictionary were written by people just
            like you. Now's your chance to add your own!
          </p>
          <p>
            Share definitions that other people will find meaningful and never
            post hate speech or people’s personal information.
          </p>
          <form>
            <form:input type="text" placeholder="Word" path="term" />
            <form:errors path="term" style="color:red;"></form:errors>
            <form:textarea
              name="definition"
              id="definition"
              cols="30"
              rows="10"
              path="definition_text"
              placeholder="Type Your Definition Here"
            ></form:textarea>
            <form:errors path="definition_text" style="color:red;"></form:errors>

            <form:input type="textarea" id="example"               cols="30"
              rows="10" placeholder="Type the associated example here" path="example"/>
              <form:errors path="example" style="color:red;"></form:errors>
            <input type="submit" value="Submit" />
          </form>
        </div>
      </section>
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
    </div>
    </form:form>
  </body>
 	<script>
 	  const errorModal = document.querySelector(".error-modal");
 	  const closeErrorModal = document.querySelector("#closeErrorModal");

 	  closeErrorModal.addEventListener("click", () => {
 	    errorModal.style.display = "none";
 	  });
 	</script>
</html>
