package day7

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

data class Directory(val name: String,
                 val parent: Directory?,
                 val directories: MutableList<Directory>,
                 val files: MutableList<DirectoryFile>){
    fun getSize() : Int{
        var size = 0
        for(file in files){
            size += file.size
        }
        for(directory in directories){
            size += directory.getSize()
        }
        return size
    }
}

data class DirectoryFile(val name: String, val size: Int)


fun getAllDirectories(dir: Directory) : List<Directory>{
    val subdirectories = dir.directories
    val returnDir = mutableListOf<Directory>()
    if(subdirectories.isEmpty()){
        return emptyList()
    }
    returnDir.addAll(dir.directories)
    for(directory in subdirectories){
        returnDir.addAll(getAllDirectories(directory))
    }
    return returnDir
}
fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day7/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?
    var root: Directory? = null
    var workingDirectory : Directory? = null

    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        if(str.contains("$ ls")){
            continue
        }
        if(str.contains("$ cd")){
            val split = str.split(" ")
            workingDirectory = when(split[2]){
                "/" -> {
                    root = Directory(split[2], null, mutableListOf(), mutableListOf())
                    root
                }
                ".." -> workingDirectory?.parent
                else -> workingDirectory?.directories?.find { child -> child.name == split[2] }
            }
            continue
        }
        if(str.startsWith("dir")){
            var split = str.split(" ")
            workingDirectory?.directories?.add(Directory(split[1], workingDirectory, mutableListOf(), mutableListOf()))
            continue
        } else {
            var split = str.split(" ")
            workingDirectory?.files?.add(DirectoryFile(split[1], split[0].toInt()))
        }
    }

    val directories : List<Directory> = getAllDirectories(root!!)
    var totalSize : Int = 0

    for(directory in directories){
        if(directory.getSize() < 100000){
            totalSize += directory.getSize()
        }
    }

    println("Total size of folders with over 10000: $totalSize")

    var availableSpace = 70000000
    var neededSpace = 30000000
    var freeSpace = availableSpace - root.getSize()

    var deleteSpace = neededSpace - freeSpace
    var totalSizeForUpdate : MutableList<Int> = mutableListOf()

    for(directory in directories){
        if(directory.getSize() >= deleteSpace){
            totalSizeForUpdate.add(directory.getSize())
        }
    }
    totalSizeForUpdate.sort()
    println("Folder to delete has a size of: ${totalSizeForUpdate.first()}")

}