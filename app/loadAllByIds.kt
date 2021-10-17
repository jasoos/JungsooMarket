 @Query("SELECT * FROM product WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Product>