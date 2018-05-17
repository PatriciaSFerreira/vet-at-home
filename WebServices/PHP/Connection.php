<?php

//Ligacao a base de dados
$conn = mysqli_connect("fdb19.awardspace.net", "2650644_vetathome", "vetathome6171", "2650644_vetathome");
//UTF-8

mysqli_set_charset($conn, 'utf8');
$returnArray = array();
if (mysqli_connect_errno()) {
    $returnArray = array(
        "success" => "0",
        "message" => "Erro na ligação à base de dados"
    );
    die('Nao foi possivel estabelecer a ligacao: ' . mysqli_error($conn));
    header('Content-type: application/json');
    echo json_encode($returnArray);
    exit();
}
?>
