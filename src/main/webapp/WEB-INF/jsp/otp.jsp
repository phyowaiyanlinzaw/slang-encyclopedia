<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://www.springframework.org/tags/form"
prefix="form"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>OTP Verification</title>
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

      .otp-form {
        width: 100%;
        max-width: 400px;
        padding: 2rem;
        border-radius: 10px;
        background-color: #1b2936;
      }

      .otp-form .input-container {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1.5rem;
        border-bottom: 1px solid #fff;
      }

      .otp-form .input-container input {
        width: 100%;
        padding: 0.5rem 0;
        font-size: 1rem;
        color: #fff;
        border: none;
        outline: none;
        background: none;
      }
      .otp-form .btn {
        width: 100%;
        padding: 0.8rem 0;
        font-size: 1rem;
        font-weight: 700;
        color: #fff;
        border: none;
        outline: none;
        border-radius: 10px;
        cursor: pointer;
        background-color: #2f80ed;
      }

      .otp-form .btn:hover {
        background-color: #2b73c7;
      }

      .otp-form .btn:focus {
        background-color: #2b73c7;
      }

      .otp-btns {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .otp-btns .btn {
        width: 48%;
      }

      .timeout-msg {
        width: 100%;
        max-width: 400px;
        padding: 2rem;
        border-radius: 10px;
        background-color: #1b2936;
      }

      .timeout-msg p {
        margin-bottom: 1rem;
        font-size: 1rem;
        font-weight: 500;
        color: #fff;
      }

      header span {
        color: #2f80ed;
      }

      .back-btn {
        width: 100%;
        padding: 0.8rem 0;
        font-size: 1rem;
        font-weight: 700;
        color: #fff;
        border: none;
        outline: none;
        border-radius: 10px;
        cursor: pointer;
        background-color: #2f80ed;
      }

      .back-btn:hover {
        background-color: #2b73c7;
      }

      .hidden {
        display: none;
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

      .error-msg {
        color: red;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <header>OTP Verification</header>
      <section>
        <form:form
          class="otp-form"
          action="/SlangEncyclopedia/ProcessOtp"
          method="POST"
          modelAttribute="otpBean"
          id="otpForm"
        >
          <div class="input-container">
            <form:input
              type="text"
              placeholder="Enter OTP"
              name="otp"
              path="otpNumber"
              id="otp"
            />
            <div class="error-msg hidden">
              <p>Invalid OTP</p>
            </div>
          </div>
          <div class="otp-btns">
            <button
              type="button"
              class="btn solid"
              id="resendOtp"
              onclick="window.location.href='/SlangEncyclopedia/UpdateOtpStatus'"
            >
              Resend
            </button>
            <input type="submit" value="Verify" class="btn solid" />
          </div>
        </form:form>
        <div class="timeout-msg hidden">
          <p>You requested 5 OTPs already. Please check your email.</p>
          <button
            class="back-btn"
            onclick="window.location.href='/SlangEncyclopedia/Register'"
          >
            Back to Register
          </button>
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
  </body>
  <script>
    const otpLimit = "${otpLimit}";
    const form = document.getElementById("otpForm");
    const timeOut = document.querySelector(".timeout-msg");

    if (otpLimit === "true") {
      form.classList.add("hidden");
      timeOut.classList.remove("hidden");
    }

    if (otpLimit == "false") {
      form.classList.remove("hidden");
      timeOut.classList.add("hidden");
    }

    const otpInput = document.getElementById("otp");
    const errorMsg = document.querySelector(".error-msg");

    otpForm.addEventListener("submit", (e) => {
      if (otpInput.value.length != 6) {
        e.preventDefault();

        errorMsg.classList.remove("hidden");

        return;
      }
    });

    const errorModal = document.querySelector(".error-modal");
    const closeErrorModal = document.querySelector("#closeErrorModal");

    closeErrorModal.addEventListener("click", () => {
      errorModal.style.display = "none";
    });
  </script>
</html>
