package com.example.pokemon.utils

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.response.seeAllPokemonResponse.Results

class ResultsPagingSource(val apiIntegration: ApiIntegration): PagingSource<Int,Results> (){
    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiIntegration.callAllPokemonAPI(nextPage)
            var nextPageNumber: Int? = null
            if(response.next != null){
                val uri = Uri.parse(response.next)
                val nextPageQuery = uri.getQueryParameter("offset")
                nextPageNumber = nextPageQuery?.toInt()
            }
            LoadResult.Page(data=response.results, prevKey = null, nextKey = nextPageNumber)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
    companion object{
        private const val FIRST_PAGE_INDEX = 1
    }
}