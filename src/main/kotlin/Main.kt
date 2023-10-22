import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


fun main() {
    choice()
}

val listOfEmp = LinkedList<Emp>()

class Emp(var name: String, var age: Int, var position: String) : Serializable

fun inputEmployee() {
    try {
        while (true) {
            print("Введите Ф.И.О. сотрудника:")
            var names:String = readLine().toString()
            print("\nВведите возраст:")
            var ages = readLine()?.trim()?.toIntOrNull() ?: 0
            print("\nВведите позицию: ")
            var positions:String = readLine().toString()
            var person = Emp(names, ages, positions )
            listOfEmp.add(person)
            println("Данные сохранены")
            break
        }
    } catch (e: Exception) {
        println("Неверно введены данные")
    }
}

fun save_to_file() {
    val file = File("employee.txt")
    for (employeee in listOfEmp) {
        var text = "${employeee.name}".format("30") + " " + "${employeee.age}".format(7) + " " + "${employeee.position }\n"
        val fileWriter = FileWriter(file, true)

        // Записываем данные в конец файла
        fileWriter.write(text)
          println()
        // Закрываем FileWriter
        fileWriter.close()

        println("Данные успешно добавлены в конец файла $file")
    }
}

fun read_and_display() {
    try {
        val path = Paths.get("employee.txt")
        println("{'Ф.И.О.'} | {'Возраст'} | {'Должность'}")
        println("-".repeat(50))
        Files.lines(path, Charsets.UTF_8).forEach { println("$it\n") }
    } catch (E: Exception) {
        println("Файл не найден")
    }
}

fun choice() {
    while (true) {
        try {
            println("Меню")
            println("1. Ввод данных о сотруднике")
            println("2. Сохранить данные в файл")
            println("3. Считать и отобразить данные из файла")
            println("4. Записать данные в бинарный файл")
            println("5. Считать бинарный файл")
            println("6 Поиск и редактирование записей (заглушка)")
            println("7 . Сортировка данных в массиве по любому элементу структуры" +
                    "(в виде заглушки).")
            println("8. Выход")
            println("Введите действие")
            var choices = readLine()?.toIntOrNull() ?: 0

            when (choices) {
                1 -> inputEmployee()
                2 -> save_to_file()
                3 -> read_and_display()
                4 -> binary_write()
                5 -> read_binary()
                6 -> binary_write_test()
                7 -> println("Еще в разработке :)")
                8 -> {
                    print("Выход")
                    break
                }
                else -> print("Не верный выбор")
            }

        } catch (e: Exception) {
            println("Неверно введен пункт. Попробуйте еще раз")
        }
    }
}

fun binary_write() {
    val fileName = "people.dat"
    try {
        val fileOutputStream = FileOutputStream(fileName)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)

        // Сериализуем список экземпляров класса и записываем его в файл
        objectOutputStream.writeObject(listOfEmp)

        objectOutputStream.close()
        fileOutputStream.close()

        println("Данные успешно сохранены в файл $fileName")
    } catch (e: Exception) {
        println("Произошла ошибка при сохранении данных: ${e.message}")
    }
}

fun read_binary() {
    val fileName = "people.dat" // Имя вашего двоичного файла

    try {
        println("{'Ф.И.О.'} | {'Возраст'} | {'Должность'}")
        println("-".repeat(50))
        val fileInputStream = FileInputStream(fileName)
        val objectInputStream = ObjectInputStream(fileInputStream)

        // Десериализуем список экземпляров класса из файла
        val loadedPeople = objectInputStream.readObject() as LinkedList<Emp>

        objectInputStream.close()
        fileInputStream.close()


        for (person in loadedPeople) {
            println("Имя: ${person.name}, Возраст: ${person.age}, Должность: ${person.position}")

        }
        println("Данные успешно загружены из файла $fileName:")
    } catch (e: Exception) {
        println("Произошла ошибка при загрузке данных: ${e.message}")
    }
}

fun binary_write_test() {
    val fileName = "people.dat"
    try {
        val fileOutputStream = FileOutputStream(fileName, true) // Открываем файл для дозаписи
        val objectOutputStream = ObjectOutputStream(fileOutputStream)

        // Сериализуем экземпляр класса и записываем его в файл
        val person = Emp("Имя", 30, "Должность") // Замените на свои данные
        objectOutputStream.writeObject(person)

        objectOutputStream.close()
        fileOutputStream.close()

        println("Данные успешно добавлены в конец файла $fileName")
    } catch (e: Exception) {
        println("Произошла ошибка при сохранении данных: ${e.message}")
    }

}
