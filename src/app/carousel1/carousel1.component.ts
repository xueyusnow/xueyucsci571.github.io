import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
@Component({
  selector: 'app-carousel1',
  templateUrl: './carousel1.component.html',
  styleUrls: ['./carousel1.component.css']
})
export class Carousel1Component implements OnInit {
  movies:any=[];
  mobile=false;

  constructor(private dataService: DataService) { }

  ngOnInit() {
    // if (window.screen.width === 414) { // 768px portrait
    //   this.mobile = true;
    //   console.log(this.mobile);
    // }
    this.dataService.sendGetRequest('http://localhost:3000/nowplaying').subscribe(data=>{
      console.log(data);
      this.movies=data;
    })  
  }

}
