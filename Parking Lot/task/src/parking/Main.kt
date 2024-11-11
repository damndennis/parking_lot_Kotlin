package parking
import java.util.Scanner

data class Car(val plateNumber: String, val color: String)
var parkingSpots = mutableListOf<Car?>()

// park command
fun parkCar(input: String): String {

    val (command, plateNumber, color) = input.split(" ")

    if (parkingSpots.isEmpty()) {
        return "Sorry, a parking lot has not been created."
    }


        for (i in parkingSpots.indices) {
            if (parkingSpots[i] == null) {

                parkingSpots[i] = Car(plateNumber, color)
                return "$color car parked in spot ${i + 1}."
            }
        }
        return "Sorry, the parking lot is full."
}

// leave command
fun leaveParking(input: String): String {
    val (command, parkingSpotNum) = input.split(" ")

    if (parkingSpots.isEmpty()) {
        return "Sorry, a parking lot has not been created."
    }


        if (parkingSpots[parkingSpotNum.toInt() - 1] != null) {
            parkingSpots[parkingSpotNum.toInt() - 1] = null
            return "Spot ${parkingSpotNum.toInt()} is free."
        }
        return "There is no car in spot ${parkingSpotNum.toInt()}."
}

// create command
fun createParking(input: String): String {
    val (command, parkSize) = input.split(" ")

        val numberOfSpots = parkSize.toIntOrNull()
        if (numberOfSpots != null && numberOfSpots > 0) {
            parkingSpots = MutableList(numberOfSpots) { null }
            return "Created a parking lot with $numberOfSpots spots."
        }
    return "Wrong input"
}

// status command
fun checkStatus(): String {
    if (parkingSpots.isEmpty()) {
        return "Sorry, a parking lot has not been created."
    }

    val parkedCars = parkingSpots.mapIndexedNotNull { index, car ->
        if (car != null) "${index + 1} ${car.plateNumber} ${car.color}" else null
    }
    return if (parkedCars.isEmpty()) {
        "Parking lot is empty."
    } else {
        parkedCars.joinToString("\n")
    }
}

// reg_by_color command
fun regByColor(input: String): String {
    val splitInput = input.split(" ")

    if (parkingSpots.isEmpty()) {
        return "Sorry, a parking lot has not been created."
    }

    val getNumberPlateByColor = parkingSpots.mapIndexedNotNull {_, car ->
        if (car != null && car.color.lowercase() == splitInput[1].lowercase()) car.plateNumber else null
    }

    return if (getNumberPlateByColor.isEmpty()) {
        "No cars with color ${splitInput[1]} were found."
    }
    else {
        getNumberPlateByColor.joinToString(", ")
    }
}

// spot_by_reg command
fun spotByReg(input: String): String {
    val splitInput = input.split(" ")

    if (parkingSpots.isEmpty()) {
        return "Sorry, a parking lot has not been created."
    }

    val getIndexByReg = parkingSpots.mapIndexedNotNull {index, car ->
        if (car != null && car.plateNumber == splitInput[1]) index + 1 else null
    }
    return if (getIndexByReg.isEmpty()) {
        "No cars with registration number ${splitInput[1]} were found."
    }
    else {
        getIndexByReg.joinToString("")
    }
}

// spot_by_color command
fun spotByColor(input: String): String {
    val splitInput = input.split(" ")
    if (parkingSpots.isEmpty()) {
        return "Sorry, a parking lot has not been created."
    }
    val getIndexByColor = parkingSpots.mapIndexedNotNull {index, car ->
        if (car != null && car.color.lowercase() == splitInput[1].lowercase()) index + 1 else null
    }
    return if (getIndexByColor.isEmpty()) {
        "No cars with color ${splitInput[1]} were found."
    }
        else {
            getIndexByColor.joinToString(", ")
        }
    }
fun main() {
    val scan = Scanner(System.`in`)

    while (true) {
        val input = scan.nextLine()

        if (input == "exit") {
            return
        }

        val response = when {
            input.startsWith("park") -> parkCar(input)
            input.startsWith("leave") -> leaveParking(input)
            input.startsWith("create") -> createParking(input)
            input.startsWith("status") -> checkStatus()
            input.startsWith("reg_by_color") -> regByColor(input)
            input.startsWith("spot_by_reg") -> spotByReg(input)
            input.startsWith("spot_by_color") -> spotByColor(input)
            else -> "unknown input"
        }

        println(response)

    }
}
