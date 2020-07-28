<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title><%=request.getParameter("title")%></title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Indie+Flower&display=swap');

    body {
      text-align: center;
      font-family: 'Indie Flower', cursive;
      font-size: 24px;
    }
    h3 { margin-bottom: 0; }
    ul {
      padding: 0;
      list-style: none;
    }
    button {
      border-radius: 10px 0 10px 0;
      border: 1px solid white;
      width: 30px;
      height: 30px;
      background-color: teal;
      color: white;
    }
    .fas {color: teal;}
    a { color: black }
    input[type=checkbox]:checked + label { text-decoration: line-through; }

  </style>
  <script src="https://kit.fontawesome.com/7b3b0349cd.js" crossorigin="anonymous"></script>
</head>


