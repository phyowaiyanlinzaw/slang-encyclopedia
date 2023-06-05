<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
    </style>
  </head>
  <body>
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
            <input type="text" placeholder="Word" />
            <input type="textarea" placeholder="Type Your Definition Here" />
            <label />
            <textarea
              name="definition"
              id="definition"
              cols="30"
              rows="10"
              placeholder="Type Your Definition Here"
            ></textarea>

            <input type="text" placeholder="Type the associated tags here" />
            <input type="submit" value="Submit" />
          </form>
        </div>
      </section>
    </div>
  </body>
</html>
