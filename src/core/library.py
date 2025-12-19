from typing import Any, Optional, Union
from models.book import Book
from models.collections import BookCollection, IndexDict


class Library: #Базовый класс библиотеки

    def __init__(self, name: str = "Библиотека"):
        self.name = name
        self.books = BookCollection()
        self.index = IndexDict()

    def __getitem__(self, key: Union[int, str, tuple]) -> Any: #Доступ к книгам библиотеки
        if isinstance(key, int):
            return self.books[key]
        else:
            return self.index[key]

    def __len__(self) -> int:
        return len(self.books)

    def __contains__(self, item: Union[Book, str]) -> bool: #Проверка наличия книги в библиотеке
        if isinstance(item, Book):
            return item in self.books
        else:
            return item in self.index

    def __call__(self, search_type: str, value: Any) -> BookCollection:
        """
        Магический метод __call__ для поиска книг
        Пример: library('author', 'Толстой')
        """
        result = BookCollection()

        if search_type == 'author':
            books = self.index[('author', value)]
            for book in books:
                result.add(book)
        elif search_type == 'year':
            books = self.index[('year', value)]
            for book in books:
                result.add(book)
        elif search_type == 'genre':
            books = self.index[('genre', value)]
            for book in books:
                result.add(book)
        elif search_type == 'isbn':
            book = self.index[value]
            if book:
                result.add(book)

        return result

    def __repr__(self) -> str:
        return f"Library(name='{self.name}', books={len(self.books)})"

    def add_book(self, book: Book) -> bool: #Добавление книги в библиотеку
        if self.books.add(book):
            self.index.add_book(book)
            return True
        return False

    def remove_book(self, isbn: str) -> bool: #Удаление книги из библиотеки по ISBN
        book = self.index[isbn]
        if book:
            if self.books.remove(book):
                self.index.remove_book(book)
                return True
        return False

    def search_by_author(self, author: str) -> BookCollection: #Поиск книг по автору
        return self('author', author)

    def search_by_year(self, year: int) -> BookCollection: #Поиск книг по году
        return self('year', year)

    def search_by_genre(self, genre: str) -> BookCollection: #Поиск книг по жанру
        return self('genre', genre)

    def search_by_isbn(self, isbn: str) -> Optional[Book]: #Поиск книги по ISBN
        return self.index[isbn]

    def search_by_title(self, title: str) -> BookCollection: #Поиск книг по названию
        result = BookCollection()
        for book in self.books:
            if title.lower() in book.title.lower():
                result.add(book)
        return result

    def update_index(self) -> None: #Обновление индексов
        self.index.update_from_collection(self.books)

    def get_stats(self) -> dict: #Получение статистики библиотеки
        return {
            'library_name': self.name,
            'total_books': len(self.books),
            'authors': self.books.get_authors(),
            'genres': self.books.get_genres(),
            'years': self.books.get_years(),
            'index_stats': self.index.get_stats()
        }


class EnhancedLibrary(Library): #Расширенный класс библиотеки с выдачей книг

    def __init__(self, name: str = "Улучшенная библиотека"):
        super().__init__(name)
        self._borrowed_books: dict[str, str] = {}  # ISBN → имя читателя

    def __repr__(self) -> str:
        return f"EnhancedLibrary(name='{self.name}', books={len(self)}, borrowed={len(self._borrowed_books)})"

    def borrow_book(self, isbn: str, reader: str) -> bool: #Выдача книги читателю
        if isbn in self.index and isbn not in self._borrowed_books:
            self._borrowed_books[isbn] = reader
            return True
        return False

    def return_book(self, isbn: str) -> bool: #Возврат книги в библиотеку
        if isbn in self._borrowed_books:
            del self._borrowed_books[isbn]
            return True
        return False

    def get_available_books(self) -> BookCollection: #Получение списка доступных книг
        available = BookCollection()
        for book in self.books:
            if book.isbn not in self._borrowed_books:
                available.add(book)
        return available

    def get_borrowed_books(self) -> dict: #Получение списка выданных книг
        borrowed = {}
        for isbn, reader in self._borrowed_books.items():
            book = self.index[isbn]
            if book:
                borrowed[isbn] = {
                    'book': book,
                    'reader': reader
                }
        return borrowed


class StatisticsLibrary(Library): #Класс библиотеки со статистикой операций

    def __init__(self, name: str = "Библиотека со статистикой"):
        super().__init__(name)
        self._stats = {
            'added': 0,
            'removed': 0,
            'searches': 0,
            'borrows': 0,
            'returns': 0
        }

    def add_book(self, book: Book) -> bool: #Добавление книги с подсчетом статистики
        self._stats['added'] += 1
        return super().add_book(book)

    def remove_book(self, isbn: str) -> bool: #Удаление книги с подсчетом статистики
        self._stats['removed'] += 1
        return super().remove_book(isbn)

    def search_by_author(self, author: str) -> BookCollection: #Поиск с подсчетом статистики
        self._stats['searches'] += 1
        return super().search_by_author(author)

    def search_by_genre(self, genre: str) -> BookCollection: #Поиск с подсчетом статистики
        self._stats['searches'] += 1
        return super().search_by_genre(genre)

    def search_by_year(self, year: int) -> BookCollection: #Поиск с подсчетом статистики
        self._stats['searches'] += 1
        return super().search_by_year(year)

    def get_statistics(self) -> dict: #Получение статистики
        stats = self._stats.copy()
        stats.update({
            'total_books': len(self.books),
            'unique_authors': len(set(b.author for b in self.books)),
            'unique_genres': len(set(b.genre for b in self.books)),
            'operations_total': sum(self._stats.values())
        })
        return stats