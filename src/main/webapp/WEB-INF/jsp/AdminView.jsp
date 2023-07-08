<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://www.springframework.org/tags/form"
prefix="form"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
    <title>Admin View</title>
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
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 15px;
      }
      header {
        width: 100%;
        height: 100px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        color: #ffffff;
        background-color: #1b2936;
      }
      .logo {
        width: 20%;
        height: 100%;
        display: flex;
        align-items: center;
      }
      .logo img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      .logo:hover {
        cursor: pointer;
      }

      .search-bar {
        width: 100%;
        height: 100px;
        display: flex;
        align-items: center;
      }
      .search-bar input {
        width: 100%;
        height: 50px;
        padding: 0 1rem;
        border: #ffffff 1px solid;
        border-radius: 5px;
        outline: none;
        font-size: 1rem;
        font-weight: 500;
        color: #ffffff;
        background-color: #1b2936;
      }
      .search-bar input::placeholder {
        color: #ffffff;
      }
      .search-bar a {
        width: 50px;
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #ffffff;
        text-decoration: none;
      }
      .search-bar a:hover {
        color: #ffffff;
        background-color: #1b2936;
      }
      section {
        width: 100%;
        padding: 1rem 0;
      }
      table {
        width: 100%;
        border-collapse: collapse;
      }
      table th {
        padding: 1rem;
        font-size: 1rem;
        font-weight: 500;
        color: #ffffff;
        text-align: left;
        background-color: #1b2936;
        border: #ffffff 0.5px solid;
      }
      table td {
        padding: 1rem;
        font-size: 1rem;
        font-weight: 500;
        color: #ffffff;
        text-align: left;
        background-color: #1b2936;
        border: #ffffff 0.5px solid;
      }
      table td a {
        color: #ffffff;
      }
      table td a:hover {
        color: #ffffff;
      }
      table tr:nth-child(even) {
        background-color: #10151b;
      }
      table tr:nth-child(odd) {
        background-color: #1b2936;
      }
      table tr:hover {
        background-color: #1b2936;
      }
      table a {
        margin: 0 0.5rem;
      }
      table a:hover {
        color: #ffffff;
      }

      .actions-td {
        display: flex;
        align-items: center;
        justify-content: center;
      }

      .sortable,
      .sortable:hover,
      .user,
      .user:hover {
        cursor: pointer;
      }

      .sortable .indicator,
      .user .indicator {
        margin-left: 5px;
      }
      .user-link {
        color: #ffffff;
        text-decoration: none;
      }
      .user-link:hover {
        color: #ffffff;
        text-decoration: underline;
      }
    </style>
  </head>
  <body>
    <header>
      <div class="logo">
        <img src="<%=request.getContextPath()%>/resource/logo.png" alt="Logo" />
      </div>
      <div class="search-bar">
        <input type="text" placeholder="Search" id="search-bar" />
      </div>
    </header>
    <section>
      <table>
        <thead>
          <tr>
            <th>No.</th>
            <th>Term</th>
            <th class="user">User</th>
            <th>Created At</th>
            <th>Updated At</th>
            <th class="sortable aesc">
              Likes <span class="indicator">↓</span>
            </th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${defList}" var="def" varStatus="inc">
            <tr>
              <td>${inc.index +1}</td>
              <td>${def.term }</td>
              <td>
                <a
                  class="user-link"
                  href="/SlangEncyclopedia/UserDefsView?createdBy=${def.createdBy}"
                  >${def.createdBy }</a
                >
              </td>
              <td>${def.createdDate}</td>
              <td>${def.updatedAt}</td>
              <td>
                <p>${def.likeCount}</p>
              </td>

              <td class="actions-td">
                <a
                  href="/SlangEncyclopedia/DeleteDefsByAdmin?definitionId=${def.defId}"
                  >Delete</a
                >
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </section>
  </body>
  <script>
    const logoBtn = document.querySelector(".logo");

    logoBtn.addEventListener("click", () => {
      window.location.href = "/SlangEncyclopedia/LogOut";
    });

    $(document).ready(function () {
      $(".aesc").click(function () {
        const $table = $(this).closest("table");
        const columnIndex = $(this).index();
        const isAscending = $(this).hasClass("aesc");

        $table
          .find("tbody tr")
          .sort(function (a, b) {
            const aValue = parseInt(
              $(a).find("td").eq(columnIndex).find("p").text()
            );
            const bValue = parseInt(
              $(b).find("td").eq(columnIndex).find("p").text()
            );

            if (isAscending) {
              return aValue - bValue;
            } else {
              return bValue - aValue;
            }
          })
          .appendTo($table.find("tbody"));

        $(this).toggleClass("aesc desc");
        $(this)
          .find(".indicator")
          .text(isAscending ? "↑" : "↓");
      });

      $(".user").click(function () {
        sortColumn($(this), "User");
      });
    });

    function sortColumn(header, column) {
      const $table = header.closest("table");
      const columnIndex = header.index();
      const isAscending = header.hasClass("aesc");

      $table
        .find("tbody tr")
        .sort(function (a, b) {
          const aValue = $(a).find("td").eq(columnIndex).text().trim();
          const bValue = $(b).find("td").eq(columnIndex).text().trim();

          if (isAscending) {
            return aValue.localeCompare(bValue);
          } else {
            return bValue.localeCompare(aValue);
          }
        })
        .appendTo($table.find("tbody"));

      header.toggleClass("aesc desc");
      header.find(".indicator").text(isAscending ? "↑" : "↓");

      groupSameNames($table, columnIndex);
    }

    function groupSameNames(table, columnIndex) {
      const rows = table.find("tbody tr");

      for (let i = 0; i < rows.length - 1; i++) {
        const currentRow = $(rows[i]);
        const nextRow = $(rows[i + 1]);

        const currentName = currentRow.find("td").eq(columnIndex).text().trim();
        const nextName = nextRow.find("td").eq(columnIndex).text().trim();

        if (currentName === nextName) {
          currentRow.insertAfter(nextRow);
        }
      }
    }

    const searchInput = document.querySelector("#search-bar");

    searchInput.addEventListener("keyup", (e) => {
      const searchString = e.target.value.toLowerCase();

      const rows = document.querySelectorAll("tbody tr");
      rows.forEach((row) => {
        const term = row.children[1].textContent.toLowerCase();
        const user = row.children[2].textContent.toLowerCase();

        if (term.includes(searchString) || user.includes(searchString)) {
          row.style.display = "table-row";
        } else {
          row.style.display = "none";
        }
      });
    });
  </script>
</html>
