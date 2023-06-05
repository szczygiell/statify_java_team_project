# PAP Projekt → Statify

## Uruchomienie
Aby uruchomić aplikację należy uruchomić plik pod nazwą 'App.java', wtedy nastąpi przekierowanie do zalogowania się do Spotify.
Alternatywną opcją jest uruchomienie pliku 'LoginWinow.java', jednak wtedy trzeba uzyskać AccessToken Spotify.

## Funkcjonalność:

### Kreator Playlist

Generowanie playlist dla użytkownika na podstawie parametrów m.in.: artysty, utworu, gatunku, nastroju lub roku wydania. Aplikacja będzie wyszukiwała podobne utwory na podstawie odpowiednich filtrów i historii użytkownika. Przykładowy algorytm wyszukiwania:

Użytkownik podaje artystę → search_artist → Get Artist’s Related Artists na podstawie id → Get Artist’s Top Tracks → tworzymy playlistę z tych utworów

### Losowy utwór dnia

Niewielka funkcja aplikacji umożliwiająca użytkownikowi poszerzenie swoich muzycznych horyzontów i wyszukanie losowego utworu dostępnego na spotify.

### Analiza statystyk

- analiza najczęściej słuchanych artystów, top piosenek użytkownika
- badanie playlist użytkownika wg m.in. średniej wartości “loudness”, “tempo”, “danceabilty”, gatunków etc.
- analiza danego autora na podstawie jego najpopularniejszych utworów i ich parametrów takich jak np. “liveness”, “energy”, “intrumentalness” itd.

## Technologie i źródła:

- Java
- [Api Spotify](https://developer.spotify.com/documentation/web-api/reference/#/)
- [Spotify api Java wrapper]( https://github.com/spotify-web-api-java/spotify-web-api-java)


### Pozostałe źródła:
ikony: https://icons8.com/icon/set/music/windows--static


### Autorzy:

Miłosz Kowalewski 

Julia Macuga

Filip Szczygielski

Mikołaj Wewiór
