package com.characters.rickandmorty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.repository.WebService
import retrofit2.HttpException

class CharacterPagingSource (private val webService: WebService): PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
      return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        return try {
            val currentPage = params.key ?: 1
            val response = webService.getAllCharactersPaging(currentPage)
            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<Character>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if(currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception){
            LoadResult.Error(e)
        }catch (http: HttpException){
            LoadResult.Error(http)
        }
    }

}