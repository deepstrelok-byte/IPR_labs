"""Тесты для модуля book.py"""
import pytest
from src.models.book import Book


class TestBook:
    """Тесты класса Book"""

    def test_book_creation(self):
        """Тест создания книги"""
        book = Book("Война и мир", "Лев Толстой", 1869, "Роман", "978-5-389-00001-1")

        assert book.title == "Война и мир"
        assert book.author == "Лев Толстой"
        assert book.year == 1869
        assert book.genre == "Роман"
        assert book.isbn == "978-5-389-00001-1"

    def test_book_repr(self):
        """Тест строкового представления"""
        book = Book("Тест", "Автор", 2020, "Жанр", "123")
        repr_str = repr(book)

        assert "Book" in repr_str
        assert "'Тест'" in repr_str
        assert "'Автор'" in repr_str

    def test_book_str(self):
        """Тест преобразования в строку"""
        book = Book("Тест", "Автор", 2020, "Жанр", "123")
        str_repr = str(book)

        assert "Тест" in str_repr
        assert "2020" in str_repr
        assert "Автор" in str_repr

    def test_book_equality(self):
        """Тест сравнения книг"""
        book1 = Book("Книга", "Автор", 2020, "Жанр", "123")
        book2 = Book("Книга", "Автор", 2020, "Жанр", "123")
        book3 = Book("Другая", "Автор", 2020, "Жанр", "456")

        assert book1 == book2  # Одинаковый ISBN
        assert book1 != book3  # Разный ISBN

    def test_book_hash(self):
        """Тест хэширования"""
        book = Book("Книга", "Автор", 2020, "Жанр", "123")

        # ISBN должен определять хэш
        assert hash(book) == hash("123")

    def test_to_dict(self):
        """Тест преобразования в словарь"""
        book = Book("Книга", "Автор", 2020, "Жанр", "123")
        book_dict = book.to_dict()

        assert book_dict["title"] == "Книга"
        assert book_dict["author"] == "Автор"
        assert book_dict["year"] == 2020
        assert book_dict["genre"] == "Жанр"
        assert book_dict["isbn"] == "123"

    def test_from_dict(self):
        """Тест создания из словаря"""
        book_dict = {
            "title": "Книга",
            "author": "Автор",
            "year": 2020,
            "genre": "Жанр",
            "isbn": "123"
        }

        book = Book.from_dict(book_dict)

        assert book.title == "Книга"
        assert book.author == "Автор"
        assert book.year == 2020
        assert book.genre == "Жанр"
        assert book.isbn == "123"