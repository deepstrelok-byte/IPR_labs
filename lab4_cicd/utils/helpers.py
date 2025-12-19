import json
from typing import List, Dict, Any
from models.book import Book
from models.collections import BookCollection


def save_books_to_file(books: BookCollection, filename: str) -> None: #Сохранение коллекции книг в JSON файл
    books_data = [book.to_dict() for book in books]

    with open(filename, 'w', encoding='utf-8') as f:
        json.dump(books_data, f, ensure_ascii=False, indent=2)


def load_books_from_file(filename: str) -> BookCollection: #Загрузка коллекции книг из JSON файла
    with open(filename, 'r', encoding='utf-8') as f:
        books_data = json.load(f)

    collection = BookCollection()
    for book_data in books_data:
        book = Book.from_dict(book_data)
        collection.add(book)

    return collection


def print_books_table(books: BookCollection, title: str = "Книги") -> None: #Красивый вывод таблицы книг
    if not books:
        print(f"\n{title}: пусто")
        return

    print(f"\n{title} ({len(books)} шт.):")
    print("=" * 100)
    print(f"{'№':<4} {'Название':<30} {'Автор':<25} {'Год':<6} {'Жанр':<20} {'ISBN':<20}")
    print("-" * 100)

    for i, book in enumerate(books, 1):
        title_short = book.title[:27] + "..." if len(book.title) > 30 else book.title
        author_short = book.author[:22] + "..." if len(book.author) > 25 else book.author
        print(f"{i:<4} {title_short:<30} {author_short:<25} {book.year:<6} {book.genre:<20} {book.isbn:<20}")

    print("=" * 100)


def print_library_stats(library) -> None: #Вывод статистики библиотеки
    print("\nСТАТИСТИКА БИБЛИОТЕКИ")
    print("=" * 50)

    stats = library.get_stats() if hasattr(library, 'get_stats') else {
        'total_books': len(library),
        'authors': library.books.get_authors(),
        'genres': library.books.get_genres(),
        'years': library.books.get_years()
    }

    print(f"Название: {stats.get('library_name', 'Библиотека')}")
    print(f"Всего книг: {stats['total_books']}")
    print(f"Авторов: {len(stats['authors'])}")
    print(f"Жанров: {len(stats['genres'])}")
    print(f"Лет издания: {len(stats['years'])}")

    if hasattr(library, 'get_borrowed_books'):
        borrowed = library.get_borrowed_books()
        print(f"Выдано книг: {len(borrowed)}")

    if 'index_stats' in stats:
        idx_stats = stats['index_stats']
        print(f"\n📈 СТАТИСТИКА ИНДЕКСОВ:")
        print(f"  Индексировано книг: {idx_stats['total_books']}")
        print(f"  Уникальных авторов: {idx_stats['unique_authors']}")
        print(f"  Уникальных годов: {idx_stats['unique_years']}")
        print(f"  Уникальных жанров: {idx_stats['unique_genres']}")


def demo_collections() -> None: #Демонстрация работы пользовательских коллекций
    print("\n" + "=" * 70)
    print("ДЕМОНСТРАЦИЯ ПОЛЬЗОВАТЕЛЬСКИХ КОЛЛЕКЦИЙ")
    print("=" * 70)

    # Создаем книги
    from models.book import Book
    book1 = Book("Война и мир", "Лев Толстой", 1869, "Роман", "978-5-389-00001-1")
    book2 = Book("Анна Каренина", "Лев Толстой", 1877, "Роман", "978-5-389-00006-6")
    book3 = Book("Преступление и наказание", "Фёдор Достоевский", 1866, "Роман", "978-5-389-00002-2")

    # Демонстрация BookCollection
    print("\n1. BookCollection:")
    collection = BookCollection()
    collection.add(book1)
    collection.add(book2)
    collection.add(book3)

    print(f"   Коллекция: {collection}")
    print(f"   Длина: {len(collection)}")
    print(f"   Первая книга: {collection[0]}")
    print(f"   Срез [1:3]: {collection[1:3]}")
    print(f"   Содержит book1? {book1 in collection}")

    # Демонстрация IndexDict
    print("\n2. IndexDict:")
    from models.collections import IndexDict
    index = IndexDict()
    index.add_book(book1)
    index.add_book(book2)
    index.add_book(book3)

    print(f"   Индекс: {index}")
    print(f"   Книга по ISBN: {index['978-5-389-00001-1']}")
    print(f"   Книги Толстого: {len(index[('author', 'Лев Толстой')])} шт.")
    print(f"   Книги 1866 года: {len(index[('year', 1866)])} шт.")
    print(f"   Содержит ISBN? {'978-5-389-00001-1' in index}")