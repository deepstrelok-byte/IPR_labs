"""Модуль симуляции библиотеки"""
import random
from typing import Optional, List
from models.book import Book
from core.library import EnhancedLibrary


class LibrarySimulation:
    """Класс для управления симуляцией библиотеки"""

    def __init__(self, library: Optional[EnhancedLibrary] = None):
        self.library = library or EnhancedLibrary("Симуляционная библиотека")
        self._events_log = []
        self._borrowed_books = []

        # Данные для генерации случайных книг
        self._titles = [
            "О дивный новый мир", "Три товарища", "Тихий Дон", "Анна Каренина",
            "Сто лет одиночества", "Улисс", "Властелин колец", "Маленький принц",
            "Собачье сердце", "Двенадцать стульев", "Золотой теленок", "Мертвые души"
        ]

        self._authors = [
            "Олдос Хаксли", "Эрих Мария Ремарк", "Михаил Шолохов", "Лев Толстой",
            "Габриэль Гарсиа Маркес", "Джеймс Джойс", "Дж. Р. Р. Толкин",
            "Антуан де Сент-Экзюпери", "Михаил Булгаков", "Ильф и Петров",
            "Николай Гоголь"
        ]

        self._genres = [
            "Роман", "Фэнтези", "Научная фантастика", "Детектив",
            "Исторический", "Биография", "Сатира", "Классика"
        ]

        self._readers = [
            "Иванов И.И.", "Петров П.П.", "Сидорова С.С.", "Кузнецов К.К.",
            "Смирнова А.А.", "Васильев В.В.", "Попова О.О."
        ]

        self._events = [
            ("add_book", 0.25),  # Добавление новой книги
            ("remove_book", 0.15),  # Удаление случайной книги
            ("search_by_author", 0.15),  # Поиск по автору
            ("search_by_genre", 0.15),  # Поиск по жанру
            ("update_index", 0.05),  # Обновление индекса
            ("try_nonexistent", 0.05),  # Попытка получить несуществующую книгу
            ("borrow_book", 0.1),  # Выдача книги
            ("return_book", 0.05),  # Возврат книги
            ("search_by_year", 0.05),  # Поиск по году
        ]

    def _generate_random_book(self) -> Book:
        """Генерация случайной книги"""
        title = random.choice(self._titles)
        author = random.choice(self._authors)
        year = random.randint(1900, 2023)
        genre = random.choice(self._genres)
        isbn = f"978-5-{random.randint(10000, 99999)}-{random.randint(100, 999)}"

        return Book(title, author, year, genre, isbn)

    def _get_event(self) -> str: #Выбор случайного события с учетом весов
        events, weights = zip(*self._events)
        return random.choices(events, weights=weights, k=1)[0]

    def _log_event(self, step: int, event: str, message: str) -> None: #Логирование события
        log_entry = f"Шаг {step:3d}: [{event:20}] {message}"
        self._events_log.append(log_entry)
        print(log_entry)

    def _add_book_event(self, step: int) -> None: #Событие: добавление книги
        book = self._generate_random_book()
        if self.library.add_book(book):
            self._log_event(step, "add_book", f"Добавлена: {book}")
        else:
            self._log_event(step, "add_book", f"Книга уже существует: {book.title}")

    def _remove_book_event(self, step: int) -> None: #Событие: удаление книги
        if len(self.library) > 0:
            random_idx = random.randint(0, len(self.library) - 1)
            book_to_remove = self.library.books[random_idx]

            if self.library.remove_book(book_to_remove.isbn):
                self._log_event(step, "remove_book", f"Удалена: {book_to_remove.title}")
            else:
                self._log_event(step, "remove_book", "Не удалось удалить книгу")
        else:
            self._log_event(step, "remove_book", "Нет книг для удаления")

    def _search_by_author_event(self, step: int) -> None: #Событие: поиск по автору
        if len(self.library) > 0:
            authors = self.library.books.get_authors()
            if authors:
                author = random.choice(authors)
                result = self.library.search_by_author(author)
                self._log_event(step, "search_by_author",
                                f"Найдено {len(result)} книг автора '{author}'")
        else:
            self._log_event(step, "search_by_author", "Нет книг для поиска")

    def _search_by_genre_event(self, step: int) -> None: #Событие: поиск по жанру
        if len(self.library) > 0:
            genre = random.choice(self._genres)
            result = self.library.search_by_genre(genre)
            self._log_event(step, "search_by_genre",
                            f"Найдено {len(result)} книг жанра '{genre}'")
        else:
            self._log_event(step, "search_by_genre", "Нет книг для поиска")

    def _search_by_year_event(self, step: int) -> None: #Событие: поиск по году"""
        if len(self.library) > 0:
            years = self.library.books.get_years()
            if years:
                year = random.choice(years)
                result = self.library.search_by_year(year)
                self._log_event(step, "search_by_year",
                                f"Найдено {len(result)} книг {year} года")
        else:
            self._log_event(step, "search_by_year", "Нет книг для поиска")

    def _update_index_event(self, step: int) -> None: #Событие: обновление индекса
        self.library.update_index()
        stats = self.library.index.get_stats()
        self._log_event(step, "update_index",
                        f"Обновлено: {stats['total_books']} книг, "
                        f"{stats['unique_authors']} авторов, "
                        f"{stats['unique_genres']} жанров")

    def _try_nonexistent_event(self, step: int) -> None: #Событие: попытка получить несуществующую книгу
        fake_isbn = "000-0-00000-000-0"
        book = self.library.search_by_isbn(fake_isbn)
        self._log_event(step, "try_nonexistent",
                        f"Книга с ISBN {fake_isbn} не найдена")

    def _borrow_book_event(self, step: int) -> None: #Событие: выдача книги
        available_books = self.library.get_available_books()
        if len(available_books) > 0:
            random_idx = random.randint(0, len(available_books) - 1)
            book_to_borrow = available_books[random_idx]
            reader = random.choice(self._readers)

            if self.library.borrow_book(book_to_borrow.isbn, reader):
                self._borrowed_books.append(book_to_borrow.isbn)
                self._log_event(step, "borrow_book",
                                f"'{book_to_borrow.title}' выдана {reader}")
            else:
                self._log_event(step, "borrow_book",
                                f"Не удалось выдать '{book_to_borrow.title}'")
        else:
            self._log_event(step, "borrow_book", "Нет доступных книг")

    def _return_book_event(self, step: int) -> None: #Событие: возврат книги
        if self._borrowed_books:
            isbn_to_return = random.choice(self._borrowed_books)
            if self.library.return_book(isbn_to_return):
                self._borrowed_books.remove(isbn_to_return)
                book = self.library.search_by_isbn(isbn_to_return)
                self._log_event(step, "return_book",
                                f"Книга '{book.title if book else isbn_to_return}' возвращена")
            else:
                self._log_event(step, "return_book",
                                f"Не удалось вернуть книгу {isbn_to_return}")
        else:
            self._log_event(step, "return_book", "Нет выданных книг")

    def run_step(self, step: int) -> None: #Выполнение одного шага симуляции
        event = self._get_event()

        event_handlers = {
            'add_book': self._add_book_event,
            'remove_book': self._remove_book_event,
            'search_by_author': self._search_by_author_event,
            'search_by_genre': self._search_by_genre_event,
            'search_by_year': self._search_by_year_event,
            'update_index': self._update_index_event,
            'try_nonexistent': self._try_nonexistent_event,
            'borrow_book': self._borrow_book_event,
            'return_book': self._return_book_event,
        }

        handler = event_handlers.get(event)
        if handler:
            handler(step)

    def get_log(self) -> List[str]: #Получение лога событий
        return self._events_log.copy()

    def get_summary(self) -> dict: #Получение итоговой статистики
        borrowed = self.library.get_borrowed_books()

        return {
            'total_books': len(self.library),
            'available_books': len(self.library.get_available_books()),
            'borrowed_books': len(borrowed),
            'total_events': len(self._events_log),
            'unique_authors': len(self.library.books.get_authors()),
            'unique_genres': len(self.library.books.get_genres()),
        }


def run_simulation(steps: int = 20, seed: Optional[int] = None) -> None:
    """
    Функция симуляции работы библиотеки

    Args:
        steps: Количество шагов симуляции
        seed: Seed для генератора случайных чисел
    """
    if seed is not None:
        random.seed(seed)
        print(f"Начало симуляции с seed={seed}")
    else:
        print("Начало симуляции")

    print("=" * 70)

    # Создаем библиотеку с начальным набором книг
    library = EnhancedLibrary("Центральная городская библиотека")

    # Начальный набор книг
    initial_books = [
        Book("Война и мир", "Лев Толстой", 1869, "Роман", "978-5-389-00001-1"),
        Book("Преступление и наказание", "Фёдор Достоевский", 1866, "Роман", "978-5-389-00002-2"),
        Book("Мастер и Маргарита", "Михаил Булгаков", 1967, "Роман", "978-5-389-00003-3"),
        Book("1984", "Джордж Оруэлл", 1949, "Антиутопия", "978-5-389-00004-4"),
        Book("Гарри Поттер и философский камень", "Дж. К. Роулинг", 1997, "Фэнтези", "978-5-389-00005-5"),
    ]

    for book in initial_books:
        library.add_book(book)

    # Создаем и запускаем симуляцию
    simulation = LibrarySimulation(library)

    for step in range(1, steps + 1):
        simulation.run_step(step)

    # Вывод итогов
    print("\n" + "=" * 70)
    print("ИТОГИ СИМУЛЯЦИИ")
    print("=" * 70)

    summary = simulation.get_summary()
    for key, value in summary.items():
        if isinstance(value, list):
            print(f"{key.replace('_', ' ').title()}: {len(value)}")
        else:
            print(f"{key.replace('_', ' ').title()}: {value}")

    # Демонстрация работы индексов
    if len(library) > 0:
        print("\nДЕМОНСТРАЦИЯ ИНДЕКСОВ:")

        # Показываем пример поиска
        if library.books.get_authors():
            sample_author = library.books.get_authors()[0]
            books_by_author = library.search_by_author(sample_author)
            print(f"  Книги автора '{sample_author}': {len(books_by_author)} шт.")

        # Статистика индексов
        index_stats = library.index.get_stats()
        print(f"  Всего в индексах: {index_stats['total_books']} книг")
        print(f"  Уникальных авторов: {index_stats['unique_authors']}")
        print(f"  Уникальных жанров: {index_stats['unique_genres']}")
        print(f"  Уникальных годов: {index_stats['unique_years']}")

    print("\nСимуляция завершена!")