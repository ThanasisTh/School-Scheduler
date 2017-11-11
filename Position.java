public class Position {
		private int i;
		private int j;
		
		public Position(int i, int j) {
			this.i = i;
			this.j = j;
		}
		
		public int getI() {
			return this.i;
		}
		
		public int getJ() {
			return this.j;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this.i==((Position)obj).i && this.j==((Position)obj).j) {
				return true;
			}
			return false;
		}
		
		
		@Override
		public int hashCode() {
			return this.i + this.j;
		}
	}