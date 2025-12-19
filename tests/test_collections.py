"""Тесты для пользовательских коллекций"""
import pytest
from src.models.book import Book
from src.models.collections import BookCollection, IndexDict


class TestBookCollection:
    """Тесты класса BookCollection"""

    @pytest.fixture
    def sample_books(self):
        """Фикстура с тестовыми книгами"""
        return [
            Book("Книга 1", "Автор 1", 2000, "Жанр 1", "111"),
            Book("Книга 2", "Автор 2", 2001, "Жанр 2", "222"),
            Book("Книга 3", "Автор 1", 2002, "Жанр 1", "333"),
        ]

    def test_initialization(self):
        """Тест инициализации пустой коллекции"""
        collection = BookCollection()
        assert len(collection) == 0

    def test_initialization_with_books(self, sample_books):
        """Тест инициализации с книгами"""
        collection = BookCollection(sample_books)
        assert len(collection) == 3

    def test_add_book(self, sample_books):
        """Тест добавления книги"""
        collection = BookCollection()
        book = sample_books[0]

        result = collection.add(book)
        assert result is True
        assert len(collection) == 1
        assert book in collection

    def test_add_duplicate_book(self, sample_books):
        """Тест добавления дубликата книги"""
        collection = BookCollection()
        book = sample_books[0]

        collection.add(book)
        result = collection.add(book)  # Добавляем второй раз

        assert result is False  # Не должно добавиться
        assert len(collection) == 1

    def test_remove_book(self, sample_books):
        """Тест удаления книги"""
        collection = BookCollection(sample_books)
        book = sample_books[0]

        result = collection.remove(book)
        assert result is True
        assert len(collection) == 2
        assert book not in collection

    def test_remove_nonexistent_book(self, sample_books):
        """Тест удаления несуществующей книги"""
        collection = BookCollection(sample_books)
        new_book = Book("Новая", "Автор", 2023, "Жанр", "999")

        result = collection.remove(new_book)
        assert result is False
        assert len(collection) == 3

    def test_get_by_isbn(self, sample_books):
        """Тест поиска по ISBN"""
        collection = BookCollection(sample_books)

        book = collection.get_by_isbn("222")
        assert book is not None
        assert book.title == "Книга 2"

        # Несуществующий ISBN
        book = collection.get_by_isbn("999")
        assert book is None

    def test_get_by_author(self, sample_books):
        """Тест поиска по автору"""
        collection = BookCollection(sample_books)

        result = collection.get_by_author("Автор 1")
        assert len(result) == 2

        result = collection.get_by_author("Неизвестный")
        assert len(result) == 0

    def test_get_by_genre(self, sample_books):
        """Тест поиска по жанру"""
        collection = BookCollection(sample_books)

        result = collection.get_by_genre("Жанр 1")
        assert len(result) == 2

        result = collection.get_by_genre("Неизвестный")
        assert len(result) == 0

    def test_get_by_year(self, sample_books):
        """Тест поиска по году"""
        collection = BookCollection(sample_books)

        result = collection.get_by_year(2001)
        assert len(result) == 1

        result = collection.get_by_year(1999)
        assert len(result) == 0

    def test_index_access(self, sample_books):
        """Тест доступа по индексу"""
        collection = BookCollection(sample_books)

        assert collection[0] == sample_books[0]
        assert collection[1] == sample_books[1]
        assert collection[2] == sample_books[2]

        # Отрицательный индекс
        assert collection[-1] == sample_books[2]

    def test_slice_access(self, sample_books):
        """Тест доступа через срез"""
        collection = BookCollection(sample_books)

        slice_result = collection[1:3]
        assert len(slice_result) == 2
        assert slice_result[0] == sample_books[1]
        assert slice_result[1] == sample_books[2]

        # Проверяем, что срез возвращает BookCollection
        assert isinstance(slice_result, BookCollection)

    def test_iteration(self, sample_books):
        """Тест итерации по коллекции"""
        collection = BookCollection(sample_books)

        books = list(collection)
        assert len(books) == 3
        assert books[0] == sample_books[0]

    def test_contains(self, sample_books):
        """Тест оператора in"""
        collection = BookCollection(sample_books)
        book = sample_books[0]
        new_book = Book("Новая", "Автор", 2023, "Жанр", "999")

        assert book in collection
        assert new_book not in collection

    def test_get_authors(self, sample_books):
        """Тест получения списка авторов"""
        collection = BookCollection(sample_books)

        authors = collection.get_authors()
        assert len(authors) == 2  # 2 уникальных автора
        assert "Автор 1" in authors
        assert "Автор 2" in authors

    def test_get_genres(self, sample_books):
        """Тест получения списка жанров"""
        collection = BookCollection(sample_books)

        genres = collection.get_genres()
        assert len(genres) == 2  # 2 уникальных жанра
        assert "Жанр 1" in genres
        assert "Жанр 2" in genres

    def test_clear(self, sample_books):
        """Тест очистки коллекции"""
        collection = BookCollection(sample_books)

        collection.clear()
        assert len(collection) == 0


class TestIndexDict:
    """Тесты класса IndexDict"""

    @pytest.fixture
    def sample_books(self):
        """Фикстура с тестовыми книгами"""
        return [
            Book("Книга 1", "Автор 1", 2000, "Жанр 1", "111"),
            Book("Книга 2", "Автор 2", 2001, "Жанр 2", "222"),
            Book("Книга 3", "Автор 1", 2000, "Жанр 1", "333"),
        ]

    def test_initialization(self):
        """Тест инициализации индекса"""
        index = IndexDict()
        assert len(index) == 0

    def test_add_book(self, sample_books):
        """Тест добавления книги в индекс"""
        index = IndexDict()
        book = sample_books[0]

        index.add_book(book)
        assert len(index) == 1
        assert "111" in index

    def test_get_by_isbn(self, sample_books):
        """Тест поиска по ISBN"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        book = index["111"]
        assert book is not None
        assert book.title == "Книга 1"

        # Несуществующий ISBN
        book = index["999"]
        assert book is None

    def test_get_by_author(self, sample_books):
        """Тест поиска по автору"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        books = index[('author', 'Автор 1')]
        assert len(books) == 2

        books = index[('author', 'Неизвестный')]
        assert len(books) == 0

    def test_get_by_year(self, sample_books):
        """Тест поиска по году"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        books = index[('year', 2000)]
        assert len(books) == 2

        books = index[('year', 1999)]
        assert len(books) == 0

    def test_get_by_genre(self, sample_books):
        """Тест поиска по жанру"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        books = index[('genre', 'Жанр 1')]
        assert len(books) == 2

        books = index[('genre', 'Неизвестный')]
        assert len(books) == 0

    def test_remove_book(self, sample_books):
        """Тест удаления книги из индекса"""
        index = IndexDict()
        book = sample_books[0]

        index.add_book(book)
        assert "111" in index

        result = index.remove_book(book)
        assert result is True
        assert "111" not in index
        assert len(index) == 0

    def test_remove_nonexistent_book(self, sample_books):
        """Тест удаления несуществующей книги"""
        index = IndexDict()
        book = sample_books[0]

        result = index.remove_book(book)
        assert result is False

    def test_update_from_collection(self, sample_books):
        """Тест обновления индекса из коллекции"""
        index = IndexDict()
        collection = BookCollection(sample_books)

        index.update_from_collection(collection)
        assert len(index) == 3

    def test_clear(self, sample_books):
        """Тест очистки индекса"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        index.clear()
        assert len(index) == 0

    def test_iteration(self, sample_books):
        """Тест итерации по ISBN"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        isbns = list(index)
        assert len(isbns) == 3
        assert "111" in isbns
        assert "222" in isbns
        assert "333" in isbns

    def test_contains(self, sample_books):
        """Тест оператора in для ISBN"""
        index = IndexDict()
        book = sample_books[0]

        index.add_book(book)
        assert "111" in index
        assert "999" not in index

    def test_get_stats(self, sample_books):
        """Тест получения статистики"""
        index = IndexDict()
        for book in sample_books:
            index.add_book(book)

        stats = index.get_stats()
        assert stats['total_books'] == 3
        assert stats['unique_authors'] == 2
        assert stats['unique_years'] == 2
        assert stats['unique_genres'] == 2