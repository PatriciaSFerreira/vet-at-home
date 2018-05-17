<?php

//Importar o ficheiro com a ligacao a BD
//A ligacao a BD devera ser colocada na variavel conn
require 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $data = json_decode(file_get_contents("php://input"));

    $username = $data->username;
    $password = $data->password;

    $sql    = "SELECT Username FROM Cliente where username = '" . $username . "' and password = '" . MD5($password) . "'";
    $result = $conn->query($sql);
    $row    = $result->fetch_array(MYSQLI_ASSOC);

    if ($row) {
        // cliente encontrado
        $returnArray = array(
            "status" => "1",
            "content" => $row['Username']
        );
    } else {
        // cliente nao encontado
        $returnArray = array(
            "status" => "0",
            "content" => "Erro na autenticacao!"
        );
    }
} else {
    $returnArray = array(
        "status" => "0",
        "content" => "Metodo nao e POST"
    );
}
header("Access-Control-Allow-Origin: *");
header('Content-type: application/json');
echo json_encode($returnArray);
?>
