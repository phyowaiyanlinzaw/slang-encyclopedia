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

      .otp-form .btn:active {
        background-color: #2b73c7;
      }

      .timer {
        font-size: 1rem;
        font-weight: 700;
        color: #fff;
      }

      .time-out {
        width: 100%;
        max-width: 400px;
        padding: 2rem;
        border-radius: 10px;
        background-color: #1b2936;
      }

      .time-out p {
        font-size: 1rem;
        font-weight: 700;
        color: #fff;
        margin-bottom: 1rem;
      }
      .go-back-btn {
        font-size: 1rem;
        font-weight: 700;
        color: #fff;
        border: none;
        outline: none;
        background-color: #2f80ed;
        cursor: pointer;
        padding: 0.5rem 1rem;
        border-radius: 10px;
        text-align: center;
      }
      .hidden
      {
      display:none;
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
        >
          <div class="input-container">
            <form:input
              type="text"
              placeholder="Enter OTP"
              name="otp"
              path="otpNumber"
              id="otp"
            />
            <p class="timer">15</p>
          </div>
          <input type="submit" value="Verify" class="btn solid" />
        </form:form>
        <div class="time-out hidden">
          <p>Timed out because too many OTPs request!!!</p>
          <button class="go-back-btn" onclick="">Back to Home</button>
        </div>
      </section>
    </div>
  </body>
  <script>
    const timer = document.querySelector(".timer");
    
    countdownTimer(5,15);

    function countdownTimer(repetitions, count) {
      let currentCount = count;
      let currentRepetition = ${otpCount};
      console.log(currentRepetition);
      const timerElement = document.querySelector(".timer");

      const timer = setInterval(() => {
        timerElement.textContent = currentCount;

        if (currentCount === 1) {
          if (currentRepetition === repetitions) {
            clearInterval(timer);
            document.querySelector(".otp-form").classList.add("hidden");
            document.querySelector(".time-out").classList.remove("hidden");
            document.querySelector(".go-back-btn").classList.remove("hidden");
          } else {
            currentCount = count;
            currentRepetition++;
            updateOtpStatus();
          }
        } else {
          currentCount--;

        }
      }, 1000);
    }
	
    function updateOtpStatus() {
      const otp = "${currentOtp}";
      
      console.log(otp);

      const xhr = new XMLHttpRequest();
      const url = "/SlangEncyclopedia/UpdateOtpStatus/" + otp;

      xhr.open("GET", url, true);

      xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
          console.log("Otp status updated");
        }
      };

      xhr.send();
    }
  </script>
</html>
