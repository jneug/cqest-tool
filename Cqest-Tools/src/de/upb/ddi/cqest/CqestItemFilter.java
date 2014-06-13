package de.upb.ddi.cqest;

import de.upb.ddi.cqest.model.CqestItem;

public interface CqestItemFilter {

	public boolean isAccepted( CqestItem item );


	public static final CqestItemFilter universalFilter = new CqestItemFilter() {
		@Override
		public boolean isAccepted( CqestItem item ) {
			return true;
		}
	};
}
