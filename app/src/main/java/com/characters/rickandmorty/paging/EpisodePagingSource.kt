package com.characters.rickandmorty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.characters.rickandmorty.data.model.Episode
import com.characters.rickandmorty.repository.WebService
import retrofit2.HttpException

class EpisodePagingSource (private val webService: WebService): PagingSource<Int, Episode>() {
    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            val currentPage = params.key ?: 1
            val response = webService.getAllEpisodesPaging(currentPage)
            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<Episode>()
            responseData.addAll(data)

            //if the data is empty, return null for cancel the request
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (http: HttpException) {
            LoadResult.Error(http)
        }
    }
}