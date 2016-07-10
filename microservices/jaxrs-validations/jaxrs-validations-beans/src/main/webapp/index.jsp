<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Validating JAX-RS resource data with Bean Validation in Java EE 7</title>
    <!-- CSS -->
    <style>
        section {
            background: #f6f6f6;
            margin-bottom: 1em;
            padding: 1em;
        }
    </style>
</head>
<body>
    <header>
        <h1>Validating JAX-RS resource data with Bean Validation in Java EE 7</h1>
    </header>
    <main>
        <section>
            <a href="${pageContext.request.contextPath}/resources/books">Get all</a>
        </section>
        <section>
            <form action="${pageContext.request.contextPath}/resources/books" method="get">
                Book id: <input type="text" id="bookId" name="id" /><br />
                <input type="button" id="getBook" value="Get book" />
            </form>
        </section>
        <section>
            <form action="${pageContext.request.contextPath}/resources/books/create" method="post">
                Book id: <input type="text" name="id" /><br />
                Book title: <input type="text" name="title" /><br />
                <input type="submit" value="Create book" />
            </form>
        </section>
    </main>
    <footer>
        <aside>By <a href="https://costajlmpp.wordpress.com/"> Joao Costa</a></aside>
    </footer>
    <script type="text/javascript">
        document.getElementById('getBook').addEventListener('click', function(event) {
            location.href = document.forms[0].action + '/' + document.getElementById('bookId').value;
        }, false);
    </script>
</body>
</html>