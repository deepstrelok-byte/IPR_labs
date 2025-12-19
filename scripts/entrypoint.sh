#!/bin/bash
# Скрипт запуска приложения

set -e

echo "Запуск библиотечной системы..."
echo "Python версия: $(python --version)"
echo "PYTHONPATH: $PYTHONPATH"

# Проверка наличия необходимых модулей
python -c "import sys; print('Python путь:', sys.path)"

# Запуск приложения
exec python -m src.main "$@"