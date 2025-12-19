"""Тесты для библиотечных классов"""
import pytest
from src.models.book import Book
from src.core.library import Library, EnhancedLibrary, StatisticsLibrary


class TestLibrary:
    """Тесты базового класса Library"""

    @pytest.fixture
    def sample_books(self):
        """Фикстура с тестовыми книгами"""
        return [
            Book("Книга 1", "Автор 1", 2000, "Жанр 1", "111"),
            Book("Книга 2", "Автор 2", 2001, "Жанр 2", "222"),
            Book("Книга 3", "Автор 1", 2000, "Жанр 1", "333"),
        ]

    def test_library_creation(self):
        """Тест создания библиотеки"""
        library = Library("Тестовая библиотека")
        assert library.name == "Тестовая библиотека"
        assert len(library) == 0

    def test_add_book(self, sample_books):
        """Тест добавления книги в библиотеку"""
        library = Library()
        book = sample_books[0]

        result = library.add_book(book)
        assert result is True
        assert len(library) == 1
        assert book in library

    def test_add_duplicate_book(self, sample_books):
        """Тест добавления дубликата книги"""
        library = Library()
        book = sample_books[0]

        library.add_book(book)
        result = library.add_book(book)  # Второй раз

        assert result is False
        assert len(library) == 1

    def test_remove_book(self, sample_books):
        """Тест удаления книги"""
        library = Library()
        book = sample_books[0]

        library.add_book(book)
        assert len(library) == 1

        result = library.remove_book("111")
        assert result is True
        assert len(library) == 0

    def test_remove_nonexistent_book(self, sample_books):
        """Тест удаления несуществующей книги"""
        library = Library()

        result = library.remove_book("999")
        assert result is False

    def test_search_by_author(self, sample_books):
        """Тест поиска по автору"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        result = library.search_by_author("Автор 1")
        assert len(result) == 2

        result = library.search_by_author("Неизвестный")
        assert len(result) == 0

    def test_search_by_year(self, sample_books):
        """Тест поиска по году"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        result = library.search_by_year(2000)
        assert len(result) == 2

        result = library.search_by_year(1999)
        assert len(result) == 0

    def test_search_by_genre(self, sample_books):
        """Тест поиска по жанру"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        result = library.search_by_genre("Жанр 1")
        assert len(result) == 2

        result = library.search_by_genre("Неизвестный")
        assert len(result) == 0

    def test_search_by_isbn(self, sample_books):
        """Тест поиска по ISBN"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        book = library.search_by_isbn("111")
        assert book is not None
        assert book.title == "Книга 1"

        book = library.search_by_isbn("999")
        assert book is None

    def test_search_by_title(self, sample_books):
        """Тест поиска по названию"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        result = library.search_by_title("Книга")
        assert len(result) == 3

        result = library.search_by_title("Неизвестная")
        assert len(result) == 0

    def test_call_method(self, sample_books):
        """Тест магического метода __call__"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        # Поиск через __call__
        result = library('author', 'Автор 1')
        assert len(result) == 2

        result = library('year', 2000)
        assert len(result) == 2

        result = library('genre', 'Жанр 1')
        assert len(result) == 2

    def test_update_index(self, sample_books):
        """Тест обновления индексов"""
        library = Library()
        for book in sample_books:
            library.add_book(book)

        library.update_index()
        stats = library.index.get_stats()
        assert stats['total_books'] == 3

    def test_get_stats(self, sample_books):
        """Тест получения статистики библиотеки"""
        library = Library("Тестовая")
        for book in sample_books:
            library.add_book(book)

        stats = library.get_stats()
        assert stats['library_name'] == "Тестовая"
        assert stats['total_books'] == 3
        assert len(stats['authors']) == 2
        assert len(stats['genres']) == 2
        assert len(stats['years']) == 2


class TestEnhancedLibrary:
    """Тесты класса EnhancedLibrary"""

    @pytest.fixture
    def sample_books(self):
        """Фикстура с тестовыми книгами"""
        return [
            Book("Книга 1", "Автор 1", 2000, "Жанр 1", "111"),
            Book("Книга 2", "Автор 2", 2001, "Жанр 2", "222"),
        ]

    def test_borrow_book(self, sample_books):
        """Тест выдачи книги"""
        library = EnhancedLibrary()
        book = sample_books[0]

        library.add_book(book)
        result = library.borrow_book("111", "Иванов И.И.")

        assert result is True
        assert len(library.get_available_books()) == 1  # Одна доступна

    def test_borrow_already_borrowed(self, sample_books):
        """Тест выдачи уже выданной книги"""
        library = EnhancedLibrary()
        book = sample_books[0]

        library.add_book(book)
        library.borrow_book("111", "Иванов И.И.")
        result = library.borrow_book("111", "Петров П.П.")  # Второй раз

        assert result is False  # Нельзя выдать уже выданную

    def test_return_book(self, sample_books):
        """Тест возврата книги"""
        library = EnhancedLibrary()
        book = sample_books[0]

        library.add_book(book)
        library.borrow_book("111", "Иванов И.И.")

        result = library.return_book("111")
        assert result is True
        assert len(library.get_available_books()) == 2  # Все доступны

    def test_return_not_borrowed(self, sample_books):
        """Тест возврата невыданной книги"""
        library = EnhancedLibrary()
        book = sample_books[0]

        library.add_book(book)
        result = library.return_book("111")  # Не выдавали

        assert result is False

    def test_get_available_books(self, sample_books):
        """Тест получения доступных книг"""
        library = EnhancedLibrary()
        for book in sample_books:
            library.add_book(book)

        library.borrow_book("111", "Иванов И.И.")
        available = library.get_available_books()

        assert len(available) == 1
        assert available[0].isbn == "222"

    def test_get_borrowed_books(self, sample_books):
        """Тест получения выданных книг"""
        library = EnhancedLibrary()
        for book in sample_books:
            library.add_book(book)

        library.borrow_book("111", "Иванов И.И.")
        borrowed = library.get_borrowed_books()

        assert len(borrowed) == 1
        assert "111" in borrowed
        assert borrowed["111"]["reader"] == "Иванов И.И."

    def test_search_shows_only_available(self, sample_books):
        """Тест, что поиск показывает только доступные книги"""
        library = EnhancedLibrary()
        for book in sample_books:
            library.add_book(book)

        library.borrow_book("111", "Иванов И.И.")

        # Поиск должен показывать только доступные
        result = library.search_by_author("Автор 1")
        assert len(result) == 0  # Книга 1 выдана

        result = library.search_by_author("Автор 2")
        assert len(result) == 1  # Книга 2 доступна


class TestStatisticsLibrary:
    """Тесты класса StatisticsLibrary"""

    @pytest.fixture
    def sample_books(self):
        """Фикстура с тестовыми книгами"""
        return [
            Book("Книга 1", "Автор 1", 2000, "Жанр 1", "111"),
            Book("Книга 2", "Автор 2", 2001, "Жанр 2", "222"),
        ]

    def test_statistics_collection(self, sample_books):
        """Тест сбора статистики"""
        library = StatisticsLibrary()

        # Выполняем операции
        library.add_book(sample_books[0])
        library.add_book(sample_books[1])
        library.remove_book("111")
        library.search_by_author("Автор 2")
        library.search_by_genre("Жанр 2")
        library.search_by_year(2001)

        stats = library.get_statistics()

        assert stats['added'] == 2
        assert stats['removed'] == 1
        assert stats['searches'] == 3  # 3 поиска
        assert stats['total_books'] == 1  # Одна осталась

    def test_statistics_operations_total(self, sample_books):
        """Тест общего количества операций"""
        library = StatisticsLibrary()

        library.add_book(sample_books[0])
        library.search_by_author("Автор")

        stats = library.get_statistics()
        assert stats['operations_total'] == 2  # 1 добавление + 1 поиск