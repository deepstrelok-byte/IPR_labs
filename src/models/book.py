from typing import Any


class Book:

    def __init__(self, title: str, author: str, year: int, genre: str, isbn: str):
        self.title = title
        self.author = author
        self.year = year
        self.genre = genre
        self.isbn = isbn

    def __repr__(self) -> str:
        return f"Book('{self.title}', '{self.author}', {self.year}, '{self.genre}', '{self.isbn}')"

    def __str__(self) -> str:
        return f"{self.title} ({self.year}) by {self.author}"

    def __eq__(self, other: Any) -> bool: #Сравнение книг по ISBN
        if isinstance(other, Book):
            return self.isbn == other.isbn
        return False

    def __hash__(self) -> int: #Хэш книги для словарей
        return hash(self.isbn)

    def to_dict(self) -> dict: #Преобразование книги в словарь
        return {
            'title': self.title,
            'author': self.author,
            'year': self.year,
            'genre': self.genre,
            'isbn': self.isbn
        }

    @classmethod
    def from_dict(cls, data: dict) -> 'Book': #Создание книги из словаря
        return cls(
            title=data['title'],
            author=data['author'],
            year=data['year'],
            genre=data['genre'],
            isbn=data['isbn']
        )