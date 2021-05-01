import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {MylistComponent} from './mylist/mylist.component';
import {DetailmComponent} from './detailm/detailm.component';
import {DetailtComponent} from './detailt/detailt.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: '', component: HomeComponent},
  {path: 'mylist', component: MylistComponent },
  {path: 'watch/movie/:id', component: DetailmComponent},
  {path: 'watch/tv/:id', component: DetailtComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 
}
