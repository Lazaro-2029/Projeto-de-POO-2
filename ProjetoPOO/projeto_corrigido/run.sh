#!/bin/bash

echo ""
echo " ============================================"
echo "   Editora Publixy — Iniciando o sistema..."
echo " ============================================"
echo ""

# Verifica Maven
if ! command -v mvn &>/dev/null; then
    echo " [ERRO] Maven não encontrado no PATH."
    echo " Instale em: https://maven.apache.org/download.cgi"
    exit 1
fi

# Verifica Java
if ! command -v java &>/dev/null; then
    echo " [ERRO] Java não encontrado no PATH."
    echo " Instale JDK 17+: https://adoptium.net"
    exit 1
fi

echo " Maven e Java encontrados. Compilando e iniciando..."
echo ""

mvn javafx:run

if [ $? -ne 0 ]; then
    echo ""
    echo " [ERRO] Falha ao iniciar."
    echo " Verifique se o MySQL está rodando e o banco foi criado com banco.sql"
fi
