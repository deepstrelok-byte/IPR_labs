"""Тесты для вспомогательных функций"""
import json
import tempfile
import os
import pytest
from src.models.book import Book
from src.models.collections import BookCollection
from src.utils.helpers import save_books_to_file, load_books_from_file, print_books_table


class TestHelpers:
    """Тесты вспомогательных функций"""

    @pytest.fixture
    def sample_books(self):
        """Фикстура с тестовыми книгами"""
        return [
            Book("Книга 1", "Автор 1", 2000, "Жанр 1", "111"),
            Book("Книга 2", "Автор 2", 2001, "Жанр 2", "222"),
        ]

    @pytest.fixture
    def sample_collection(self, sample_books):
        """Фикстура с тестовой коллекцией"""
        collection = BookCollection()
        for book in sample_books:
            collection.add(book)
        return collection

    def test_save_and_load_books(self, sample_collection):
        """Тест сохранения и загрузки книг"""
        with tempfile.NamedTemporaryFile(mode='w', suffix='.json', delete=False) as f:
            filename = f.name

        try:
            # Сохраняем
            save_books_to_file(sample_collection, filename)

            # Проверяем, что файл создан
            assert os.path.exists(filename)

            # Загружаем
            loaded_collection = load_books_from_file(filename)

            # Проверяем содержимое
            assert len(loaded_collection) == 2

            # Проверяем первую книгу
            book1 = loaded_collection.get_by_isbn("111")
            assert book1 is not None
            assert book1.title == "Книга 1"
            assert book1.author == "Автор 1"

            # Проверяем вторую книгу
            book2 = loaded_collection.get_by_isbn("222")
            assert book2 is not None
            assert book2.title == "Книга 2"
            assert book2.author == "Автор 2"

        finally:
            # Удаляем временный файл
            if os.path.exists(filename):
                os.remove(filename)

    def test_save_empty_collection(self):
        """Тест сохранения пустой коллекции"""
        with tempfile.NamedTemporaryFile(mode='w', suffix='.json', delete=False) as f:
            filename = f.name

        try:
            empty_collection = BookCollection()
            save_books_to_file(empty_collection, filename)

            # Проверяем, что файл создан
            assert os.path.exists(filename)

            # Проверяем содержимое файла
            with open(filename, 'r', encoding='utf-8') as f:
                data = json.load(f)

            assert data == []

        finally:
            if os.path.exists(filename):
                os.remove(filename)

    def test_load_nonexistent_file(self):
        """Тест загрузки из несуществующего файла"""
        with pytest.raises(FileNotFoundError):
            load_books_from_file("nonexistent.json")

    def test_print_books_table_empty(self, capsys):
        """Тест вывода пустой таблицы книг"""
        empty_collection = BookCollection()
        print_books_table(empty_collection, "Пустая коллекция")

        captured = capsys.readouterr()
        assert "Пустая коллекция: пусто" in captured.out

    def test_print_books_table_with_books(self, sample_collection, capsys):
        """Тест вывода таблицы с книгами"""
        print_books_table(sample_collection, "Тестовая коллекция")

        captured = capsys.readouterr()

        # Проверяем заголовок
        assert "Тестовая коллекция" in captured.out
        assert "2 шт." in captured.out

        # Проверяем заголовки столбцов
        assert "Название" in captured.out
        assert "Автор" in captured.out
        assert "Год" in captured.out
        assert "Жанр" in captured.out
        assert "ISBN" in captured.out

        # Проверяем данные книг
        assert "Книга 1" in captured.out
        assert "Автор 1" in captured.out
        assert "2000" in captured.out
        assert "Жанр 1" in captured.out
        assert "111" in captured.out