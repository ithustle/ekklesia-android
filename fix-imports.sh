#!/bin/bash

# Caminho base do projeto
BASE_DIR="./app/src/main/java/com/toquemedia"

echo "🔍 Corrigindo pacotes e imports de seedfy para ekklesia..."

# 1. Renomeia os pacotes dentro dos arquivos
find "$BASE_DIR/ekklesia" -name "*.kt" -type f -exec sed -i '' 's/com\.toquemedia\.seedfy/com.toquemedia.ekklesia/g' {} +

# 2. Remove imports quebrados que não foram atualizados corretamente
find "$BASE_DIR/ekklesia" -name "*.kt" -type f -exec sed -i '' '/import .*seedfy/d' {} +

# 3. (Opcional) Comentar linhas com referências a símbolos ainda quebrados
# Lista de símbolos problemáticos (adicione mais conforme aparecem)
SYMBOLS=("AlarmScheduler" "seedfy")

for SYMBOL in "${SYMBOLS[@]}"
do
    echo "⚠️ Comentando usos de símbolo não resolvido: $SYMBOL"
    find "$BASE_DIR/ekklesia" -name "*.kt" -type f -exec sed -i '' "s/^\(.*\b$SYMBOL\b.*\)$/\/\/ TODO: REVER - \1/" {} +
done

echo "✅ Correção em massa concluída."

