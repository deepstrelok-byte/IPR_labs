"""Тесты для симуляции"""
import pytest
from src.core.simulations import LibrarySimulation, run_simulation
from src.models.book import Book


class TestLibrarySimulation:
    """Тесты класса LibrarySimulation"""

    def test_simulation_creation(self):
        """Тест создания симуляции"""
        simulation = LibrarySimulation()

        assert simulation.library.name == "Симуляционная библиотека"
        assert len(simulation.library) == 0
        assert len(simulation.get_log()) == 0

    def test_generate_random_book(self):
        """Тест генерации случайной книги"""
        simulation = LibrarySimulation()
        book = simulation._generate_random_book()

        assert book.title is not None
        assert book.author is not None
        assert 1900 <= book.year <= 2023
        assert book.genre is not None
        assert book.isbn is not None
        assert book.isbn.startswith("978-5-")

    def test_log_event(self):
        """Тест логирования событий"""
        simulation = LibrarySimulation()

        simulation._log_event(1, "test_event", "Тестовое сообщение")
        log = simulation.get_log()

        assert len(log) == 1
        assert "Шаг 1:" in log[0]
        assert "[test_event]" in log[0]
        assert "Тестовое сообщение" in log[0]

    def test_get_summary(self):
        """Тест получения итоговой статистики"""
        simulation = LibrarySimulation()

        # Добавим книгу и выдадим ее
        book = Book("Тест", "Автор", 2020, "Жанр", "123")
        simulation.library.add_book(book)
        simulation.library.borrow_book("123", "Читатель")

        summary = simulation.get_summary()

        assert summary['total_books'] == 1
        assert summary['available_books'] == 0
        assert summary['borrowed_books'] == 1
        assert summary['total_events'] == 0  # Еще не было событий симуляции

    def test_run_step_adds_event_to_log(self):
        """Тест, что шаг симуляции добавляет событие в лог"""
        simulation = LibrarySimulation()

        simulation.run_step(1)
        log = simulation.get_log()

        assert len(log) == 1
        assert "Шаг 1:" in log[0]


def test_run_simulation_function():
    """Тест функции run_simulation"""
    # Запускаем короткую симуляцию с seed для воспроизводимости
    # Не проверяем вывод, просто убеждаемся, что не падает
    try:
        run_simulation(steps=2, seed=42)
        assert True
    except Exception as e:
        pytest.fail(f"run_simulation вызвала исключение: {e}")