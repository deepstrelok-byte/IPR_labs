from __future__ import annotations
from typing import Any, Union, List, Dict, Optional, Iterator
from collections.abc import Sequence
from models.book import Book


class BaseCollection:

    def __init__(self):
        self._data = []

    def __len__(self) -> int:
        return len(self._data)

    def __iter__(self) -> Iterator:
        return iter(self._data)

    def __repr__(self) -> str:
        return f"{self.__class__.__name__} with {len(self)} items"

    def clear(self) -> None:
        self._data.clear()


class BookCollection(BaseCollection, Sequence): #Пользовательская списочная коллекция книг через композицию

    def __init__(self, books: Optional[List[Book]] = None):
        super().__init__()
        self._books = books or []

    def __getitem__(self, key: Union[int, slice]) -> Union[Book, BookCollection]: #Поддержка доступа по индексу и срезам
        if isinstance(key, slice):
            return BookCollection(self._books[key])
        return self._books[key]

    def __len__(self) -> int:
        return len(self._books)

    def __contains__(self, book: Book) -> bool: #метод для проверки наличия книги"""
        return any(b.isbn == book.isbn for b in self._books)

    def __repr__(self) -> str:
        if not self._books:
            return "BookCollection(empty)"

        book_titles = []
        for book in self._books[:3]:
            title = book.title
            if len(title) > 20:
                title = title[:17] + "..."
            book_titles.append(f"'{title}'")

        if len(self._books) > 3:
            return f"BookCollection({len(self)} books: {', '.join(book_titles)}...)"
        return f"BookCollection({len(self)} books: {', '.join(book_titles)})"

    def add(self, book: Book) -> bool: #Добавление книги в коллекцию
        if book not in self:
            self._books.append(book)
            return True
        return False

    def remove(self, book: Book) -> bool: #Удаление книги из коллекции
        try:
            self._books.remove(book)
            return True
        except ValueError:
            return False

    def remove_by_isbn(self, isbn: str) -> bool: #Удаление книги по ISBN
        for book in self._books:
            if book.isbn == isbn:
                self._books.remove(book)
                return True
        return False

    def get_by_isbn(self, isbn: str) -> Optional[Book]: #Поиск книги по ISBN
        for book in self._books:
            if book.isbn == isbn:
                return book
        return None

    def get_by_author(self, author: str) -> BookCollection: #Поиск книг по автору
        result = BookCollection()
        for book in self._books:
            if book.author == author:
                result.add(book)
        return result

    def get_by_genre(self, genre: str) -> BookCollection: #Поиск книг по жанру
        result = BookCollection()
        for book in self._books:
            if book.genre.lower() == genre.lower():
                result.add(book)
        return result

    def get_by_year(self, year: int) -> BookCollection: #Поиск книг по году
        result = BookCollection()
        for book in self._books:
            if book.year == year:
                result.add(book)
        return result

    def get_authors(self) -> List[str]: #Получение списка авторов
        return list(set(book.author for book in self._books))

    def get_genres(self) -> List[str]: #Получение списка жанров
        return list(set(book.genre for book in self._books))

    def get_years(self) -> List[int]: #Получение списка годов
        return list(set(book.year for book in self._books))

    def to_list(self) -> List[Book]: #Преобразование в список
        return self._books.copy()

    def clear(self) -> None: #Очистка коллекции
        self._books.clear()


class IndexDict(BaseCollection): #Пользовательская словарная коллекция для индексации книг

    def __init__(self):
        super().__init__()
        self._isbn_index: Dict[str, Book] = {}
        self._author_index: Dict[str, List[Book]] = {}
        self._year_index: Dict[int, List[Book]] = {}
        self._genre_index: Dict[str, List[Book]] = {}

    def __getitem__(self, key: Union[str, int, tuple]) -> Any:
        """
        Доступ по ключу:
        - str: поиск по ISBN
        - tuple: ('author', name), ('year', year), ('genre', genre)
        """
        if isinstance(key, tuple):
            index_type, value = key
            if index_type == 'author':
                return self._author_index.get(value, [])
            elif index_type == 'year':
                return self._year_index.get(value, [])
            elif index_type == 'genre':
                return self._genre_index.get(value, [])
            else:
                raise KeyError(f"Unknown index type: {index_type}")
        else:
            return self._isbn_index.get(key)

    def __iter__(self) -> Iterator[str]: #Итерация по ISBN
        return iter(self._isbn_index.keys())

    def __len__(self) -> int:
        return len(self._isbn_index)

    def __contains__(self, isbn: str) -> bool: #Проверка наличия книги по ISBN
        return isbn in self._isbn_index

    def __repr__(self) -> str:
        stats = []
        if self._isbn_index:
            stats.append(f"{len(self._isbn_index)} ISBNs")
        if self._author_index:
            stats.append(f"{len(self._author_index)} authors")
        if self._year_index:
            stats.append(f"{len(self._year_index)} years")
        if self._genre_index:
            stats.append(f"{len(self._genre_index)} genres")
        return f"IndexDict({', '.join(stats)})"

    def add_book(self, book: Book) -> None: #Добавление книги во все индексы
        # Индекс по ISBN
        self._isbn_index[book.isbn] = book

        # Индекс по автору
        if book.author not in self._author_index:
            self._author_index[book.author] = []
        self._author_index[book.author].append(book)

        # Индекс по году
        if book.year not in self._year_index:
            self._year_index[book.year] = []
        self._year_index[book.year].append(book)

        # Индекс по жанру
        if book.genre not in self._genre_index:
            self._genre_index[book.genre] = []
        self._genre_index[book.genre].append(book)

    def remove_book(self, book: Book) -> bool: #Удаление книги из всех индексов
        if book.isbn not in self._isbn_index:
            return False

        # Удаляем из индекса по ISBN
        del self._isbn_index[book.isbn]

        # Удаляем из индекса по автору
        if book.author in self._author_index:
            self._author_index[book.author].remove(book)
            if not self._author_index[book.author]:
                del self._author_index[book.author]

        # Удаляем из индекса по году
        if book.year in self._year_index:
            self._year_index[book.year].remove(book)
            if not self._year_index[book.year]:
                del self._year_index[book.year]

        # Удаляем из индекса по жанру
        if book.genre in self._genre_index:
            self._genre_index[book.genre].remove(book)
            if not self._genre_index[book.genre]:
                del self._genre_index[book.genre]

        return True

    def update_from_collection(self, collection: BookCollection) -> None: #Обновление индексов из коллекции книг
        self.clear()
        for book in collection:
            self.add_book(book)

    def clear(self) -> None: #Очистка всех индексов
        self._isbn_index.clear()
        self._author_index.clear()
        self._year_index.clear()
        self._genre_index.clear()

    def get_stats(self) -> dict: #Получение статистики индексов
        return {
            'total_books': len(self._isbn_index),
            'unique_authors': len(self._author_index),
            'unique_years': len(self._year_index),
            'unique_genres': len(self._genre_index)
        }