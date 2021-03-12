from flask import Flask, jsonify, request
import json
# import requests
from datetime import timedelta
from datetime import datetime
import urllib.request

app = Flask(__name__)
prefix="https://image.tmdb.org/t/p/original"

@app.route('/')
def homepage():
    return app.send_static_file("index.html")

@app.route('/trending', methods=['GET', 'POST'])
def get_top_trending():
    url1="https://api.themoviedb.org/3/trending/movie/week?api_key=38ed65c377771b36628b1df55ae458b9"
    # response1 = requests.request("GET", url1).json()
    res=urllib.request.Request(url1)
    req=urllib.request.urlopen(res)
    response1=json.loads(req.read())
    trending = response1['results']
    results=[]
    for i in range(5):
        result={}
        trend = trending[i]
        result['title']=trend['title']
        if(trend['backdrop_path'] != '' and trend['backdrop_path'] != None):
        	result['backdrop_path']=prefix+trend['backdrop_path']
        else:
        	result['backdrop_path']="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png"
        result['release_date']=trend['release_date'][:4]
        results.append(result)
    return json.dumps(results)


@app.route('/airingtoday', methods=['GET', 'POST'])
def get_top_airingtoday():
    url2="https://api.themoviedb.org/3/tv/airing_today?api_key=38ed65c377771b36628b1df55ae458b9"
    # response2 = requests.request("GET", url2).json()
    res=urllib.request.Request(url2)
    req=urllib.request.urlopen(res)
    response2=json.loads(req.read())
    airtoday = response2['results']
    results=[]
    for i in range(5):
        result={}
        air = airtoday[i]
        result['name']=air['name']
        if(air['backdrop_path'] != '' and air['backdrop_path'] != None):
        	result['backdrop_path']=prefix+air['backdrop_path']
        else:
        	result['backdrop_path']="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png"
        result['first_air_date']=air['first_air_date'][:4]
        results.append(result)
    return json.dumps(results)
def getgenres():
    genreurl="https://api.themoviedb.org/3/genre/movie/list?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US"
    # genresall=requests.request("GET", genreurl).json()
    res=urllib.request.Request(genreurl)
    req=urllib.request.urlopen(res)
    genresall=json.loads(req.read())
    genres=genresall['genres'] 
    dic={}
    for genre in genres:
        dic[genre['id']]=" "+genre['name'] 
    return dic

@app.route('/movie', methods=['GET', 'POST'])
def searchmovie():
    genres=getgenres()
    keyword = request.args.get("keyword")
    # category = request.args.get('category')
    keyword=keyword.replace(" ","%20")
    url="https://api.themoviedb.org/3/search/movie?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&query="+keyword+"&page=1&include_adult=false"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    movies = response['results']
    results=[]
    for i in range(len(movies)):
        result={}
        movie = movies[i]
        result['id']=movie['id']
        result['title']=movie['title']
        result['overview']=movie['overview']
        if(movie['poster_path'] != '' and movie['poster_path'] != None):
        	result['poster_path']=prefix+movie['poster_path']
        else:
        	result['poster_path']="https://cinemaone.net/images/movie_placeholder.png"
        result['release_date']=movie['release_date'][:4]
        result['vote_average']=movie['vote_average']
        b=movie['vote_average']
        a=str(float(b)/2.0)
        result['vote_average']=a+"/5"
        result['vote_count']=movie['vote_count']
        list2=[]
        list1=movie['genre_ids']
        for l in list1:
            if genres.get(l) is not None:
                list2.append(genres.get(l))
        result['genre_ids']=list2
        results.append(result)
    return json.dumps(results)


@app.route('/tv', methods=['GET', 'POST'])
def searchtv():
    genres=getgenres()
    keyword = request.args.get('keyword')
    # category = request.args.get('category')
    keyword=keyword.replace(" ","%20")
    url="https://api.themoviedb.org/3/search/tv?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&query="+keyword+"&page=1&include_adult=false"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    tvs = response['results']
    results=[]
    for i in range(len(tvs)):
        result={}
        tv = tvs[i]
        result['id']=tv['id']
        result['name']=tv['name']
        result['overview']=tv['overview']
        if(tv['poster_path'] != '' and tv['poster_path'] != None):
        	result['poster_path']=prefix+tv['poster_path']
        else:
        	result['poster_path']="https://cinemaone.net/images/movie_placeholder.png"
        if tv.get('first_air_date')!=None and tv.get('first_air_date')!='':
            result["first_air_date"]= tv.get('first_air_date')[:4]
        b=tv['vote_average']
        a=str(float(b)/2.0)
        result['vote_average']=a+"/5"
        result['vote_count']=tv['vote_count']
        list2=[]
        list1=tv['genre_ids']
        for l in list1:
            if genres.get(l) is not None:
                list2.append(genres.get(l))
        result['genre_ids']=list2
        results.append(result)
    return json.dumps(results)

@app.route('/both', methods=['GET', 'POST'])
def searchboth():
    genres=getgenres()
    keyword = request.args.get('keyword')
    # category = request.args.get('category')
    keyword=keyword.replace(" ","%20")
    url="https://api.themoviedb.org/3/search/multi?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&query="+keyword+"&page=1&include_adult=false"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    boths = response['results']
    results=[]
    for i in range(len(boths)):
        both=boths[i]
        result={}
        if both['media_type']=="movie":
            result['id']=both['id']
            result['title']=both['title']
            result['overview']=both['overview']
            if(both['poster_path'] != '' and both['poster_path'] != None):
            	result['poster_path']=prefix+both['poster_path']
            else:
            	result['poster_path']="https://cinemaone.net/images/movie_placeholder.png"
            result['release_date']=both['release_date'][:4]
            # result['vote_average']=both['vote_average']
            b=both['vote_average']
            a=str(float(b)/2.0)
            result['vote_average']=a+"/5"
            result['vote_count']=both['vote_count']
            list2=[]
            list1=both['genre_ids']
            for l in list1:
                if genres.get(l) is not None:
                    list2.append(genres.get(l))
            result['genre_ids']=list2
            result['media_type']=both['media_type']
            results.append(result)
        elif both['media_type']=="tv":
            result['id']=both['id']
            result['name']=both['name']
            result['overview']=both['overview']
            if(both['poster_path'] != '' and both['poster_path'] != None):
            	result['poster_path']=prefix+both['poster_path']
            else:
            	result['poster_path']="https://cinemaone.net/images/movie_placeholder.png"
            result['first_air_date']=both['first_air_date'][:4]
            # result['vote_average']=both['vote_average']
            b=both['vote_average']
            a=str(float(b)/2.0)
            result['vote_average']=a+"/5"
            result['vote_count']=both['vote_count']
            list2=[]
            list1=both['genre_ids']
            for l in list1:
                if genres.get(l) is not None:
                    list2.append(genres.get(l))
            result['genre_ids']=list2
            result['media_type']=both['media_type']
            results.append(result)
    return json.dumps(results)

@app.route('/moviedetails', methods=['GET', 'POST'])
def moviedetails():
    id = request.args.get('id')
    url="https://api.themoviedb.org/3/movie/"+id+"?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US"
    # movie = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    movie=json.loads(req.read())
    result={}
    result['id']=movie['id']
    result['title']=movie['title']
    result['runtime']=movie['runtime']
    ll=[]
    for tri in movie['spoken_languages']:
        ll.append(tri['name'])
    result['spoken_languages']=ll
    result['overview']=movie['overview']
    if(movie['poster_path'] != '' and movie['poster_path'] != None):
    	result['poster_path']=prefix+movie['poster_path']
    else:
    	result['poster_path']="https://cinemaone.net/images/movie_placeholder.png"
    if(movie['backdrop_path'] != '' and movie['backdrop_path'] != None):
    	result['backdrop_path']=prefix+movie['backdrop_path']
    else:
    	result['backdrop_path']="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png"
    result['release_date']=movie['release_date'][:4]
    b=movie['vote_average']
    a=str(float(b)/2.0)
    result['vote_average']=a+"/5"
    result['vote_count']=movie['vote_count']
    l3=[]
    for  tri in movie['genres']:
        string=" "+str(tri['name'])
        l3.append(string)
    result['genres']=l3
    return json.dumps(result)

@app.route('/moviecredits', methods=['GET', 'POST'])
def moviecredits():
    id = request.args.get('id')
    url="https://api.themoviedb.org/3/movie/"+id+"/credits?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    movies = response['cast']
    results=[]
    for i in range(min(len(movies),8)):
        result={}
        movie=movies[i]
        result['name']=movie['name']
        if(movie['profile_path'] != '' and movie['profile_path'] != None):
        	result['profile_path']=prefix+movie['profile_path']
        else:
        	result['profile_path']="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png"
        
        result['character']=movie['character']
        results.append(result)
    return json.dumps(results)


@app.route('/moviereviews', methods=['GET', 'POST'])
def moviereviews():
    id = request.args.get('id')
    url="https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    movies = response['results']
    results=[]
    for i in range(min(len(movies),5)):
        result={}
        movie=movies[i]
        result['username']=movie['author_details']['username']
        result['content']=movie['content']
        if(movie['author_details']['rating']!=None):
            b=movie['author_details']['rating']
            # a=b.split("/")
            a=str(float(b)/2.0)
            result['rating']=a+"/5"
        else:
            result['rating']=''
        result['created_at']=datetime.strptime(movie['created_at'][:10], '%Y-%m-%d').date().strftime('%m/%d/%Y')
        results.append(result)
    return json.dumps(results)

@app.route('/tvdetails', methods=['GET', 'POST'])
def tvdetails():
    id = request.args.get('id')
    url="https://api.themoviedb.org/3/tv/"+id+"?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US"
    # movie = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    movie=json.loads(req.read())
    result={}
    result['id']=movie['id']
    result['name']=movie['name']
    result['episode_run_time']=movie['episode_run_time']
    result['number_of_seasons']=movie['number_of_seasons']
    ll=[]
    for tri in movie['spoken_languages']:
        ll.append(tri['name'])
    result['spoken_languages']=ll
    result['overview']=movie['overview']
    if(movie['poster_path'] != '' and movie['poster_path'] != None):
    	result['poster_path']=prefix+movie['poster_path']
    else:
    	result['poster_path']="https://cinemaone.net/images/movie_placeholder.png"
    if(movie['backdrop_path'] != '' and movie['backdrop_path'] != None):
    	result['backdrop_path']=prefix+movie['backdrop_path']
    else:
    	result['backdrop_path']="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png"
    result['first_air_date']=movie['first_air_date'][:4]
    b=movie['vote_average']
    a=str(float(b)/2.0)
    result['vote_average']=a+"/5"
    result['vote_count']=movie['vote_count']
    l3=[]
    for  tri in movie['genres']:
        string=" "+str(tri['name'])
        l3.append(string)
    result['genres']=l3
    return json.dumps(result)

@app.route('/tvcredits', methods=['GET', 'POST'])
def tvcredits():
    id = request.args.get('id')
    url="https://api.themoviedb.org/3/tv/"+id+"/credits?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    movies = response['cast']
    results=[]
    for i in range(min(len(movies),8)):
	    result={}
	    movie=movies[i]
	    result['name']=movie['name']
	    if(movie['profile_path'] != '' and movie['profile_path'] != None):
	    	result['profile_path']=prefix+movie['profile_path']
	    else:
	    	result['profile_path']="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png"
	    
	    result['character']=movie['character']
	    results.append(result)
    return json.dumps(results)


@app.route('/tvreviews', methods=['GET', 'POST'])
def tvreviews():
    id = request.args.get('id')
    url="https://api.themoviedb.org/3/tv/"+id+"/reviews?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1"
    # response = requests.request("GET", url).json()
    res=urllib.request.Request(url)
    req=urllib.request.urlopen(res)
    response=json.loads(req.read())
    movies = response['results']
    results=[]
    for i in range(min(len(movies),5)):
        result={}
        movie=movies[i]
        result['username']=movie['author_details']['username']
        result['content']=movie['content']
        if(movie['author_details']['rating']!=None):
            b=movie['author_details']['rating']
            # a=b.split("/")
            a=str(float(b)/2.0)
            result['rating']=a+"/5"
        else:
            result['rating']=''
        result['created_at']=datetime.strptime(movie['created_at'][:10], '%Y-%m-%d').date().strftime('%m/%d/%Y')
        results.append(result)
    return json.dumps(results)


if __name__ == "__main__":
    app.config['DEBUG']=True
    app.config['SEND_FILE_MAX_AGE_DEFAULT']=timedelta(seconds=1)
    app.debug = True
    app.run()
