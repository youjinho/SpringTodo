<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
    <title>Todo List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<body>
    <div class="container" style="max-width:600px;">
        <h1>Todo List</h1>
        <div class="input-group mb-3">
            <input type="text" class="form-control" id="todo" placeholder="할일">
            <input type="date" class="form-control" id="date">
            <div class="input-group-append">
                <button class="btn btn-outline-primary" type="button" id="add">추가</button>
            </div>
        </div>
        <ul class="list-group" id="todo-list">
        </ul>
        <button class="btn btn-outline-primary mt-3" type="button" id="upload">서버에 업로드</button>
        <button class="btn btn-outline-primary mt-3" type="button" id="download">서버에서 다운로드</button>
        <button class="btn btn-outline-primary mt-3" type="button" id="init">데이터베이스 초기화</button>
    </div>

    <script>
        $(document).ready(function () {
            let todos = [];
            $("#add").click(function () {
                let todo = $("#todo").val();
                let date = $("#date").val();
                if (todo && date) {
                    todos.push({ todo: todo, date: date, done: false });
                    $("#todo").val("");
                    $("#date").val("");
                    refreshList();
                }
            });

            $("#upload").click(function () {
                $.ajax({
                    url: '/todo/upload',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(todos),
                    success: function () {
                        alert('서버에 업로드 완료');
                    },
                    error: function () {
                        alert('서버에 업로드 실패');
                    }
                });
            });

            $("#download").click(function () {
                $.ajax({
                    url: '/todo/download',
                    method: 'GET',
                    success: function (data) {
                        todos = data;
                        refreshList();
                        alert('서버로부터 다운로드 완료');
                    },
                    error: function () {
                        alert('서버로부터 다운로드 실패');
                    }
                });
            });

            $("#init").click(function () {
                $.ajax({
                    url: '/todo/init',
                    method: 'GET',
                    success: function (data) {
                        todos = [];
                        refreshList();
                        alert('데이터베이스 초기화 완료');
                    },
                    error: function () {
                        alert('데이터베이스 초기화 실패');
                    }
                });
            });

            function refreshList() {
                $("#todo-list").empty();
                todos.forEach((item, index) => {
                    $("#todo-list").append(
                        `<li class="list-group-item">
                            <input type="checkbox" ${item.done ? 'checked' : ''} onclick="toggleDone(${index})">
                            \${item.todo} (\${item.date})
                            <button type="button" class="close" aria-label="Close" onclick="deleteItem(${index})">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </li>`
                    );
                });
            }

            window.toggleDone = function (index) {
                todos[index].done = !todos[index].done;
                refreshList();
            }

            window.deleteItem = function (index) {
                todos.splice(index, 1);
                refreshList();
            }
        });
    </script>
</body>

</html>
