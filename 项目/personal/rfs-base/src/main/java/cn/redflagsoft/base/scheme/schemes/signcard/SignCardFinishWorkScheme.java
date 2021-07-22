package cn.redflagsoft.base.scheme.schemes.signcard;

/****
 * 	
 * @author lifeng
 *	¹ý´í½áÊø
 */
public class SignCardFinishWorkScheme extends AbstractSignCardWorkScheme{

	@Override
	public Object doScheme() {
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return super.doScheme();
	}
}
