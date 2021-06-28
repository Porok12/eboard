import {Directive, ElementRef} from '@angular/core';

@Directive({
  selector: '[clickable]',
  host: {
    '(mouseenter)': 'mouseEntered()',
    '(mouseleave)': 'mouseLeave()'
  }
})
export class ClickableDirective {

  constructor(private ref: ElementRef) { }

  mouseEntered() {
    this.ref.nativeElement.style.transform = 'scale(1.1)';
    this.ref.nativeElement.style.cursor = 'pointer';
  }

  mouseLeave() {
    this.ref.nativeElement.style.transform = 'scale(1.0)';
    this.ref.nativeElement.style.cursor = 'auto';
  }
}
