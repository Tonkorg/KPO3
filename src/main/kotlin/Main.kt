import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.io.IOException


val binaryname = "people.dat"
val workFile = "employee.txt"
val binary_file_for_array = "binary_file.bin"

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
    val file = File(workFile)
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
        val path = Paths.get(workFile)
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
                6 -> println("Еще в разработке :)")
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

    try {
        val fileOutputStream = FileOutputStream(binaryname)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)

        // Сериализуем список экземпляров класса и записываем его в файл
        objectOutputStream.writeObject(listOfEmp)

        objectOutputStream.close()
        fileOutputStream.close()

        println("Данные успешно сохранены в файл $binaryname")
    } catch (e: Exception) {
        println("Произошла ошибка при сохранении данных: ${e.message}")
    }
}

//fun choice_file(ch: Int) {
//    if (ch == 2) {
//        while (true) {
//            println("Выбирите файл для подкачки:")
//            println("1) binary_file.bin \n2)people.dat")
//            when (readLine()?.toInt()) {
//                1 -> {read_binary(); break}
//                2 -> {readFromBinaryFile(binary_file_for_array);break}
//                else -> println("Выведите один из представленных вариантов")
//            }
//        }
//    }
//    if(ch ==1)
//    {
//        while (true) {
//            println("Выбирите файл для записи:")
//            println("1) binary_file.bin \n2)people.dat")
//            when (readLine()?.toInt()) {
//                1 -> {binary_array();break}
//                2 -> {binary_write(); break}
//                else -> println("Выведите один из представленных вариантов")
//            }
//        }
//    }
//    }
//fun read_binary() {
//    val loadedPeople = readFromBinaryFile(binaryname)
//
//    if (loadedPeople.isNotEmpty()) {
//        println("Данные успешно загружены из файла $binaryname:")
//        for (person in loadedPeople) {
//            println("Имя: ${person.name}, Возраст: ${person.age}, Должность: ${person.position}")
//        }
//    } else {
//        println("Ошибка при загрузке данных из бинарного файла.")
//    }
//}


/*
fun binary_array() {
    try {
        val fos = FileOutputStream(binary_file_for_array)

        for (emp in listOfEmp) {
            val nameBytes = emp.name.toByteArray(Charsets.UTF_8)
            val ageBytes = emp.age.toString().toByteArray(Charsets.UTF_8)
            val positionBytes = emp.position.toByteArray(Charsets.UTF_8)
            val data = nameBytes + ageBytes + positionBytes
            fos.write(data)
        }

        fos.close()

        println("Данные успешно записаны в бинарный файл: $binary_file_for_array")
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
fun stringToBinary(input: String): ByteArray {
    val binaryList = mutableListOf<Byte>()

    for (char in input) {
        val binaryValue = char.toInt().toString(2)
        // Дополните нулями, если необходимо
        val paddedBinaryValue = binaryValue.padStart(8, '0')
        binaryList.add(paddedBinaryValue.toByte(2))
    }

    return binaryList.toByteArray()
}

fun readFromBinaryFile(binaryFileName: String): List<Emp> {
    try {
        val fileInputStream = FileInputStream(binaryFileName)
        val objectInputStream = ObjectInputStream(fileInputStream)

        // Десериализуем список экземпляров класса из файла
        val loadedPeople = objectInputStream.readObject() as List<Emp>

        objectInputStream.close()
        fileInputStream.close()

        return loadedPeople
    } catch (e: IOException) {
        println("Произошла ошибка при чтении данных из бинарного файла: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("Произошла ошибка при чтении данных из бинарного файла: ${e.message}")
    }

    return emptyList()
}

*/
fun read_binary() {
    try {
        println("{'Ф.И.О.'} | {'Возраст'} | {'Должность'}")
        println("-".repeat(50))
        val fileInputStream = FileInputStream(binaryname)
        val objectInputStream = ObjectInputStream(fileInputStream)

        // Десериализуем список экземпляров класса из файла
        val loadedPeople = objectInputStream.readObject() as LinkedList<Emp>

        objectInputStream.close()
        fileInputStream.close()


        for (person in loadedPeople) {
            println("Имя: ${person.name}, Возраст: ${person.age}, Должность: ${person.position}")

        }
        println("Данные успешно загружены из файла $binaryname:")
    } catch (e: Exception) {
        println("Произошла ошибка при загрузке данных: ${e.message}")
    }
}
