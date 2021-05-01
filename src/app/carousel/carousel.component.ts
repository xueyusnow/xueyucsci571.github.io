import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {map,switchMap} from 'rxjs/operators';
export class Item {
  id:string='';
  title:string='';
  backdrop_path:string='';
}
@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {
  movies:Item[]=[];
  mobile=false;

  constructor(private dataService: DataService) { }

  ngOnInit() {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
    this.dataService.sendGetRequest('http://localhost:3000/nowplaying').subscribe(data=>{

      console.log(data);
      this.movies=<Item[]>data;
    })  
  }

}
