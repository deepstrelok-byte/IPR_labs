import sys
from core.simulations import run_simulation
from utils.helpers import demo_collections, print_library_stats, print_books_table
from core.library import Library, EnhancedLibrary, StatisticsLibrary
from models.book import Book


def main():
    print("=" * 70)
    print("Лабораторная работа 'Симуляция с пользовательскими коллекциями'")
    print("=" * 70)

    # Демонстрация работы коллекций
    demo_collections()

    print("\n" + "=" * 70)
    print("ДЕМОНСТРАЦИЯ РАБОТЫ БИБЛИОТЕК")
    print("=" * 70)

    # 1. Демонстрация базовой библиотеки
    print("\n1. Базовая библиотека:")
    basic_lib = Library("Основная библиотека")

    books = [
        Book("Война и мир", "Лев Толстой", 1869, "Роман", "978-5-389-00001-1"),
        Book("Анна Каренина", "Лев Толстой", 1877, "Роман", "978-5-389-00006-6"),
        Book("Преступление и наказание", "Фёдор Достоевский", 1866, "Роман", "978-5-389-00002-2"),
    ]

    for book in books:
        basic_lib.add_book(book)

    print(f"   {basic_lib}")
    print(f"   Поиск Толстого: {len(basic_lib.search_by_author('Лев Толстой'))} книг")

    # 2. Демонстрация расширенной библиотеки
    print("\n2. Расширенная библиотека:")
    enhanced_lib = EnhancedLibrary("Библиотека с выдачей")

    for book in books:
        enhanced_lib.add_book(book)

    enhanced_lib.borrow_book("978-5-389-00001-1", "Иванов И.И.")
    print(f"   {enhanced_lib}")
    print(f"   Доступные книги: {len(enhanced_lib.get_available_books())}")

    # 3. Демонстрация библиотеки со статистикой
    print("\n3. Библиотека со статистикой:")
    stats_lib = StatisticsLibrary("Статистическая библиотека")

    for book in books:
        stats_lib.add_book(book)

    stats_lib.search_by_author("Лев Толстой")
    stats_lib.search_by_genre("Роман")

    stats = stats_lib.get_statistics()
    print(f"   {stats_lib}")
    print(f"   Статистика операций: {stats}")

    print("\n" + "=" * 70)
    print("ЗАПУСК СИМУЛЯЦИИ")
    print("=" * 70)

    # Запуск симуляции с разными параметрами
    print("\nВарианты запуска:")
    print("1. Короткая симуляция (10 шагов) без seed")
    print("2. Средняя симуляция (20 шагов) с seed=42")
    print("3. Длинная симуляция (50 шагов) с seed=123")
    print("4. Выход")

    while True:
        try:
            choice = input("\nВыберите вариант (1-4): ").strip()

            if choice == '1':
                run_simulation(steps=10, seed=None)
                break
            elif choice == '2':
                run_simulation(steps=20, seed=42)
                break
            elif choice == '3':
                run_simulation(steps=50, seed=123)
                break
            elif choice == '4':
                print("\nВыход из программы.")
                sys.exit(0)
            else:
                print("Неверный выбор. Попробуйте снова.")

        except KeyboardInterrupt:
            print("\n\nПрограмма прервана пользователем.")
            sys.exit(0)
        except Exception as e:
            print(f"\nОшибка: {e}")
            sys.exit(1)


if __name__ == "__main__":
    main()