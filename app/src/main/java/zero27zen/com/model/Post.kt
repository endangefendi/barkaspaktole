package zero27zen.com.model




class Post{

    var id: String? = null
    var Nama: String? = null
    var gambar:String?= null
    var harga: Int? = null

    constructor() {}

    constructor(id: String, Nama: String,gambar:String, harga: Int) {
        this.id = id
        this.Nama = Nama
        this.gambar=gambar
        this.harga = harga
    }

    constructor(Nama: String,gambar: String, harga: Int) {
        this.Nama = Nama
        this.gambar=gambar
        this.harga = harga
    }
    constructor(Nama: String, harga: Int) {
        this.Nama = Nama
        this.harga = harga
    }
    constructor(Nama: String) {
        this.Nama = Nama
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("Nama", Nama!!)
        result.put("gambar",gambar!!)
        result.put("harga", harga!!)

        return result
    }

}