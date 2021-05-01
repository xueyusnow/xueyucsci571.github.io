import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MylistComponent } from './mylist/mylist.component';
import { CarouselComponent } from './carousel/carousel.component';
import {HttpClientModule} from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { MoviesComponent } from './movies/movies.component';
import { DetailmComponent } from './detailm/detailm.component';
import { DetailtComponent } from './detailt/detailt.component';
import {YouTubePlayerModule} from '@angular/youtube-player';
import { TvsComponent } from './tvs/tvs.component';
import { FooterComponent } from './footer/footer.component';
import { PersonmodelComponent } from './personmodel/personmodel.component';
import { PersonextmodelComponent } from './personextmodel/personextmodel.component';
import { FormsModule } from '@angular/forms';
import { ContinueComponent } from './continue/continue.component';
import { Movies1Component } from './movies1/movies1.component';
import { Carousel1Component } from './carousel1/carousel1.component';
import { Tvs1Component } from './tvs1/tvs1.component';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MylistComponent,
    CarouselComponent,
    HomeComponent,
    MoviesComponent,
    DetailmComponent,
    DetailtComponent,
    TvsComponent,
    FooterComponent,
    PersonmodelComponent,
    PersonextmodelComponent,
    ContinueComponent,
    Movies1Component,
    Carousel1Component,
    Tvs1Component,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    YouTubePlayerModule,
    FormsModule,
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
// let apiLoaded=false;
