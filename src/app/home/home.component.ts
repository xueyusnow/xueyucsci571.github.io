import { Component, OnInit} from '@angular/core';
import {DataService} from '../data.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  mobile=false;
  constructor(private dataService: DataService) {
   }

  ngOnInit() {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
  }
  
}
